package data.refinery.boilerplate;

import data.refinery.schema.EntityFieldReadWriteAccessor;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

public interface DocumentReadWriteAccessors extends DocumentReadAccessors, DocumentWriteAccessors, EntityFieldReadWriteAccessor {

    default DocumentSchema getSchema() {
        return documentSchema();
    }

}
