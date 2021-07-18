package data.refinery.conversion;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;
import data.refinery.schema.SimpleEntity;

import static com.google.common.base.Preconditions.checkNotNull;

public class SimpleEntityFactory implements EntityFactory<SimpleEntity, SimpleEntity> {

    private final EntitySchema schema;

    public SimpleEntityFactory(EntitySchema schema) {
        this.schema = checkNotNull(schema);
    }

    @Override
    public EntitySchema getSchema() {
        return schema;
    }

    @Override
    public SimpleEntity newBuilder() {
        return new SimpleEntity(schema);
    }

    @Override
    public SimpleEntity newBuilder(EntityFieldReadAccessor entity) {
        if (!entity.getSchema().equals(getSchema())) {
            throw new IllegalArgumentException("Trying to assign " + entity.getSchema() + " to " + getSchema());
        }
        SimpleEntity result = new SimpleEntity(getSchema());
        for (Field field : result.getSchema().getFields()) {
            result.setValueOfField(field, entity.getValueOfField(field));
        }
        return result;
    }

    @Override
    public SimpleEntity toBuilder(SimpleEntity entity) {
        return new SimpleEntity(entity);
    }

    @Override
    public SimpleEntity build(SimpleEntity entity) {
        return new SimpleEntity(entity);
    }
}
