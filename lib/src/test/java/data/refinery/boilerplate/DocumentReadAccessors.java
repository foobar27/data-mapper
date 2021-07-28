package data.refinery.boilerplate;


import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.Field;
import data.refinery.schema.NoSuchFieldException;

import java.net.URL;
import java.time.ZonedDateTime;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

public interface DocumentReadAccessors extends EntityFieldReadAccessor {

    URL getUrl();

    ZonedDateTime getPublicationDate();

    String getTitle();

    String getContent();

    boolean isComment();

    @Override
    default DocumentSchema getSchema() {
        return documentSchema();
    }

    @Override
    default Object getValueOfField(Field field) {
        if (field == documentSchema().url()) {
            return getUrl();
        } else if (field == documentSchema().publicationDate()) {
            return getPublicationDate();
        } else if (field == documentSchema().title()) {
            return getTitle();
        } else if (field == documentSchema().content()) {
            return getContent();
        } else if (field == documentSchema().isComment()) {
            return isComment();
        }
        throw new NoSuchFieldException(getSchema(), field);
    }


}
