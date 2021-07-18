package data.refinery.mapping;

import com.google.common.base.MoreObjects;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static data.refinery.schema.NoSuchFieldException.checkFieldExists;

public final class ImmutableEntityMapping implements EntityMapping {

    private final EntitySchema inputSchema;
    private final EntitySchema outputSchema;
    private final Map<Field, Field> mapping;
    private final Map<Field, Field> reverseMapping;

    private ImmutableEntityMapping(Builder builder) {
        this.inputSchema = builder.inputSchema;
        this.outputSchema = builder.outputSchema;
        this.mapping = builder.mapping;
        this.reverseMapping = builder.reverseMapping;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("ImmutableEntityMapping")
                .add("inputSchema", inputSchema)
                .add("outputSchema", outputSchema)
                .add("mapping", mapping)
                .add("reverseMapping", reverseMapping)
                .toString();
    }

    @Override
    public EntitySchema getInputSchema() {
        return inputSchema;
    }

    @Override
    public EntitySchema getOutputSchema() {
        return outputSchema;
    }

    @Override
    public Map<Field, Field> getMapping() {
        return mapping;
    }

    @Override
    public Map<Field, Field> getReverseMapping() {
        return reverseMapping;
    }

    public static Builder newBuilder(EntitySchema inputSchema, EntitySchema outputSchema) {
        return new Builder(inputSchema, outputSchema);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {

        private EntitySchema inputSchema;
        private EntitySchema outputSchema;
        private final Map<Field, Field> mapping;
        private final Map<Field, Field> reverseMapping;

        private Builder(EntitySchema inputSchema, EntitySchema outputSchema) {
            this.inputSchema = checkNotNull(inputSchema);
            this.outputSchema = checkNotNull(outputSchema);
            this.mapping = new HashMap<>();
            this.reverseMapping = new HashMap<>();
        }

        private Builder(ImmutableEntityMapping immutable) {
            this.inputSchema = immutable.inputSchema;
            this.outputSchema = immutable.outputSchema;
            this.mapping = new HashMap<>(immutable.mapping);
            this.reverseMapping = new HashMap<>(immutable.reverseMapping);
        }

        public Builder normalizeInputSchema() {
            this.inputSchema = inputSchema.filterKeys(mapping.keySet());
            return this;
        }

        public Builder normalizeOutputSchema() {
            this.outputSchema = outputSchema.filterKeys(new HashSet<>(mapping.values()));
            return this;
        }

        public Builder mapField(Field from, Field to) {
            checkFieldExists(inputSchema, from);
            checkFieldExists(outputSchema, to);
            if (mapping.containsKey(from)) {
                throw new IllegalArgumentException(String.format("Ambiguous mapping from %s to %s (source already mapped to %s)", from, to, mapping.get(from)));
            }
            mapping.put(from, to);
            if (reverseMapping.containsKey(to)) {
                throw new IllegalArgumentException(String.format("Ambiguous mapping from %s to %s (target already reverse mapped from %s)", from, to, reverseMapping.get(to)));
            }
            reverseMapping.put(to, from);
            return this;
        }

        public ImmutableEntityMapping build() {
            return new ImmutableEntityMapping(this);
        }

    }

}
