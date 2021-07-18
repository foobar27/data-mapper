package data.refinery.boilerplate;

import data.refinery.schema.EntityFieldWriteAccessor;
import data.refinery.schema.Field;

import static data.refinery.boilerplate.PersonSchema.personSchema;

public interface PersonWriteAccessors extends EntityFieldWriteAccessor {

    void setFirstName(String value);

    void setLastName(String value);

    void setFullName(String value);

    void setAge(int value);

    default PersonSchema getSchema() {
        return personSchema();
    }

    default void setValueOfField(Field field, Object value) {
        if (field == personSchema().firstName()) {
            setFirstName((String) value);
        } else if (field == personSchema().lastName()) {
            setLastName((String) value);
        } else if (field == personSchema().fullName()) {
            setFullName((String) value);
        } else if (field == personSchema().age()) {
            setAge((int) value);
        }
    }

}
