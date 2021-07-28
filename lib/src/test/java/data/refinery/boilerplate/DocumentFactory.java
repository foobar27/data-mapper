package data.refinery.boilerplate;

import data.refinery.conversion.EntityFactory;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntitySchema;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

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
