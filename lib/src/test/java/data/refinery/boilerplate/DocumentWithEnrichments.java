package data.refinery.boilerplate;

import data.refinery.pipeline.AbstractEntityWithEnrichments;
import data.refinery.pipeline.ValueOrEnrichment;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

public class DocumentWithEnrichments extends AbstractEntityWithEnrichments {

    private final ValueOrEnrichment url = register(documentSchema().url());
    private final ValueOrEnrichment publicationDate = register(documentSchema().publicationDate());
    private final ValueOrEnrichment title = register(documentSchema().title());
    private final ValueOrEnrichment content = register(documentSchema().content());
    private final ValueOrEnrichment isComment = register(documentSchema().isComment());

    public DocumentWithEnrichments() {
        super(documentSchema());
    }

    public ValueOrEnrichment url() {
        return url;
    }

    public ValueOrEnrichment publicationDate() {
        return publicationDate;
    }

    public ValueOrEnrichment title() {
        return title;
    }

    public ValueOrEnrichment content() {
        return content;
    }

    public ValueOrEnrichment isComment() {
        return isComment;
    }

}
