package data.refinery.boilerplate;


import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.Field;
import data.refinery.schema.NoSuchFieldException;

import static data.refinery.boilerplate.PersonSchema.personSchema;

public interface PersonReadAccessors extends EntityFieldReadAccessor {

    String getFirstName();

    String getLastName();

    String getFullName();

    int getAge();

    @Override
    default PersonSchema getSchema() {
        return personSchema();
    }

    @Override
    default Object getValueOfField(Field field) {
        if (field == personSchema().firstName()) {
            return getFirstName();
        } else if (field == personSchema().lastName()) {
            return getLastName();
        } else if (field == personSchema().fullName()) {
            return getFullName();
        } else if (field == personSchema().age()) {
            return getAge();
        }
        throw new NoSuchFieldException(getSchema(), field);
    }


}
