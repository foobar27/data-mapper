package data.refinery.schema;

import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public final class EmptyEntityFieldReadAccessor implements EntityFieldReadAccessor {

    private static final EmptyEntityFieldReadAccessor instance = new EmptyEntityFieldReadAccessor();

    public static EntityFieldReadAccessor getInstance() {
        return instance;
    }

    private EmptyEntityFieldReadAccessor() {
        // disable public constructor
    }

    @Override
    public EntitySchema getSchema() {
        return EmptyEntitySchema.getInstance();
    }

    @Override
    public Object getValueOfField(Field field) {
        throw new NoSuchFieldException(getSchema(), field);
    }

    @Override
    public EntityFieldReadAccessor filterFields(Set<Field> fields) {
        checkArgument(fields.isEmpty());
        return this;
    }

    @Override
    public void mergeInto(EntityFieldReadWriteAccessor output) {
        EntityFieldReadAccessor.super.mergeInto(output);
    }
}
