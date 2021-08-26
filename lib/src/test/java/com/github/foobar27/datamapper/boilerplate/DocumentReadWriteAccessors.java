package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;

import static com.github.foobar27.datamapper.boilerplate.DocumentSchema.documentSchema;

public interface DocumentReadWriteAccessors extends DocumentReadAccessors, DocumentWriteAccessors, EntityFieldReadWriteAccessor {

    default DocumentSchema getSchema() {
        return documentSchema();
    }

}
