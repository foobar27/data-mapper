package data.refinery.mapping;

import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableProfunctorEntityMapping implements ProfunctorEntityMapping {

    private final ImmutableEntityMapping leftMapping;
    private final ImmutableEntityMapping rightMapping;

    private ImmutableProfunctorEntityMapping(Builder builder) {
        this.leftMapping = builder.leftMapping.build();
        this.rightMapping = builder.rightMapping.build();
    }

    @Override
    public ImmutableEntityMapping getLeftMapping() {
        return leftMapping;
    }

    @Override
    public ImmutableEntityMapping getRightMapping() {
        return rightMapping;
    }

    public static Builder newBuilder(
            EntitySchema leftInputSchema,
            EntitySchema leftOutputSchema,
            EntitySchema rightInputSchema,
            EntitySchema rightOutputSchema) {
        return new Builder(
                checkNotNull(leftInputSchema),
                checkNotNull(leftOutputSchema),
                checkNotNull(rightInputSchema),
                checkNotNull(rightOutputSchema));
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {

        private final ImmutableEntityMapping.Builder leftMapping;
        private final ImmutableEntityMapping.Builder rightMapping;

        private Builder(EntitySchema leftInputSchema,
                        EntitySchema leftOutputSchema,
                        EntitySchema rightInputSchema,
                        EntitySchema rightOutputSchema) {
            this.leftMapping = ImmutableEntityMapping.newBuilder(leftInputSchema, leftOutputSchema);
            this.rightMapping = ImmutableEntityMapping.newBuilder(rightInputSchema, rightOutputSchema);
        }

        private Builder(ImmutableProfunctorEntityMapping mapping) {
            this.leftMapping = mapping.getLeftMapping().toBuilder();
            this.rightMapping = mapping.getRightMapping().toBuilder();
        }

        public Builder leftMapField(Field from, Field to) {
            leftMapping.mapField(from, to);
            return this;
        }

        public Builder rightMapField(Field from, Field to) {
            rightMapping.mapField(from, to);
            return this;
        }

        public ImmutableProfunctorEntityMapping build() {
            // TODO verify consistency (all fields mapped)
            return new ImmutableProfunctorEntityMapping(this);
        }

    }

}
