package com.github.foobar27.datamapper.boilerplate;


import com.github.foobar27.datamapper.schema.Field;
import com.github.foobar27.datamapper.schema.FluentEntitySchema;

public final class DocumentSchema extends FluentEntitySchema {

    private static final DocumentSchema instance = new DocumentSchema();

    public static DocumentSchema documentSchema() {
        return instance;
    }

    private final Field url = register("url");
    private final Field publicationDate = register("publicationDate");
    private final Field title = register("title");
    private final Field content = register("content");
    private final Field isComment = register("isComment");

    private DocumentSchema() {
        super("Document");
    }

    public Field url() {
        return url;
    }

    public Field publicationDate() {
        return publicationDate;
    }

    public Field title() {
        return title;
    }

    public Field content() {
        return content;
    }

    public Field isComment() {
        return isComment;
    }

}
