package data.refinery.mapping;

import data.refinery.schema.NoSuchFieldException;
import data.refinery.schema.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class EntityMappingView implements EntityFieldReadAccessor {

    private final EntityFieldReadAccessor delegate;
    private final EntityMapping mapping;
    private final EntitySchema schema;

    public EntityMappingView(EntityFieldReadAccessor delegate, EntityMapping mapping) {
        this.delegate = checkNotNull(delegate);
        this.mapping = checkNotNull(mapping);
        this.schema = ImmutableEntitySchema.newBuilder()
                .addAllFields(mapping.getMapping().values())
                .build();
    }

    @Override
    public EntitySchema getSchema() {
        return schema;
    }

    @Override
    public Object getValueOfField(Field field) {
        Field mappedField = mapping.getReverseMapping().get(field);
        if (mappedField == null) {
            throw new NoSuchFieldException(getSchema(), field);
        }
        return delegate.getValueOfField(mappedField);
    }
}
