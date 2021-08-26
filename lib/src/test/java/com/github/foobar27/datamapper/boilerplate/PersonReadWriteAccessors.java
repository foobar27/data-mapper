package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;

public interface PersonReadWriteAccessors extends PersonReadAccessors, PersonWriteAccessors, EntityFieldReadWriteAccessor {

    @Override
    default PersonSchema getSchema() {
        return PersonSchema.personSchema();
    }
}
