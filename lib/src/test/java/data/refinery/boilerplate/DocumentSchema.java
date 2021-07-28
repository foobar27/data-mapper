package data.refinery.boilerplate;


import data.refinery.schema.Field;
import data.refinery.schema.FluentEntitySchema;

public final class DocumentSchema extends FluentEntitySchema {

    private static final DocumentSchema instance = new DocumentSchema();

    public static DocumentSchema documentSchema() {
        return instance;
    }

    private final Field url = register("url");
    private final Field publicationDate = register("publicationDate");
    private final Field title = register("title");
    private final Field content = register("content");

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


}