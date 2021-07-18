package data.refinery.example;

import data.refinery.schema.EntityFieldReadWriteAccessor;

public interface PersonReadWriteAccessors extends PersonReadAccessors, PersonWriteAccessors, EntityFieldReadWriteAccessor {

    @Override
    default PersonSchema getSchema() {
        return PersonSchema.personSchema();
    }
}
