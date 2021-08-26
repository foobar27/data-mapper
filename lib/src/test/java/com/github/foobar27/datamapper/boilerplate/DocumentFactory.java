package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.conversion.EntityFactory;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;

import static com.github.foobar27.datamapper.boilerplate.DocumentSchema.documentSchema;

public class DocumentFactory implements EntityFactory<ImmutableDocument, ImmutableDocument.Builder> {

    @Override
    public EntitySchema getSchema() {
        return documentSchema();
    }

    @Override
    public ImmutableDocument.Builder newBuilder() {
        return ImmutableDocument.newBuilder();
    }

    @Override
    public ImmutableDocument.Builder newBuilder(EntityFieldReadAccessor entity) {
        // TODO optimization if "entity instanceof DocumentReadAccessors"
        return ImmutableDocument.newBuilder(entity);
    }

    @Override
    public ImmutableDocument.Builder toBuilder(ImmutableDocument entity) {
        return entity.toBuilder();
    }

    @Override
    public ImmutableDocument build(ImmutableDocument.Builder builder) {
        return builder.build();
    }
}
