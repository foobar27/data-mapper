package data.mapper.schema;

// TODO remove (migrated)
public interface EntityReadWriteAccessor<Schema extends EntitySchema> extends EntityReadAccessor<Schema>, EntityWriteAccessor<Schema> {

    default EntityReadAccessor unmodifiable() {
        return this;
    }

}
