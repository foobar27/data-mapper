package data.refinery.boilerplate;

import data.refinery.schema.EntityFieldWriteAccessor;
import data.refinery.schema.Field;

import java.net.URI;
import java.time.ZonedDateTime;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;
import static data.refinery.boilerplate.PersonSchema.personSchema;

public interface DocumentWriteAccessors extends EntityFieldWriteAccessor {

    void setUrl(URI value);

    void setPublicationDate(ZonedDateTime value);

    void setTitle(String value);

    void setContent(String value);

    default DocumentSchema getSchema() {
        return documentSchema();
    }

    default void setValueOfField(Field field, Object value) {
        if (field == documentSchema().url()) {
            setUrl((URI) value);
        } else if (field == documentSchema().publicationDate()) {
            setPublicationDate((ZonedDateTime) value);
        } else if (field == documentSchema().title()) {
            setTitle((String) value);
        } else if (field == documentSchema().content()) {
            setContent((String) value);
        }
    }

}
