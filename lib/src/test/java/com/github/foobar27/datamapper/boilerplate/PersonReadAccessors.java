package com.github.foobar27.datamapper.boilerplate;


import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.Field;
import com.github.foobar27.datamapper.schema.NoSuchFieldException;

import static com.github.foobar27.datamapper.boilerplate.PersonSchema.personSchema;

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
