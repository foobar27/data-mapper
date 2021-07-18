package data.refinery.conversion;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.EntitySchema;

public interface EntityFactory<Type extends EntityFieldReadAccessor, BuilderType extends EntityFieldReadWriteAccessor> {

    EntitySchema getSchema();

    BuilderType newBuilder();

    BuilderType newBuilder(EntityFieldReadAccessor entity);

    BuilderType toBuilder(Type entity);

    Type build(BuilderType builder);

}
