package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.pipeline.AbstractEntityWithEnrichments;
import com.github.foobar27.datamapper.pipeline.TypedValueOrEnrichment;

import java.net.URL;
import java.time.ZonedDateTime;

import static com.github.foobar27.datamapper.boilerplate.DocumentSchema.documentSchema;

public class DocumentWithEnrichments extends AbstractEntityWithEnrichments {

    private final TypedValueOrEnrichment<URL> url = register(documentSchema().url());
    private final TypedValueOrEnrichment<ZonedDateTime> publicationDate = register(documentSchema().publicationDate());
    private final TypedValueOrEnrichment<String> title = register(documentSchema().title());
    private final TypedValueOrEnrichment<String> content = register(documentSchema().content());
    private final TypedValueOrEnrichment<Boolean> isComment = register(documentSchema().isComment());

    public DocumentWithEnrichments() {
        super(documentSchema());
    }

    public TypedValueOrEnrichment<URL> url() {
        return url;
    }

    public TypedValueOrEnrichment<ZonedDateTime> publicationDate() {
        return publicationDate;
    }

    public TypedValueOrEnrichment<String> title() {
        return title;
    }

    public TypedValueOrEnrichment<String> content() {
        return content;
    }

    public TypedValueOrEnrichment<Boolean> isComment() {
        return isComment;
    }

}
