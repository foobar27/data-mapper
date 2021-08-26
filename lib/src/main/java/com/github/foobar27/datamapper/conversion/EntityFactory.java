package com.github.foobar27.datamapper.conversion;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;

public interface EntityFactory<Type extends EntityFieldReadAccessor, BuilderType extends EntityFieldReadWriteAccessor> {

    EntitySchema getSchema();

    BuilderType newBuilder();

    BuilderType newBuilder(EntityFieldReadAccessor entity);

    BuilderType toBuilder(Type entity);

    Type build(BuilderType builder);

}
