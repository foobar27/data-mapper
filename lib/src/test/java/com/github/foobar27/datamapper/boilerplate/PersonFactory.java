package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.conversion.EntityFactory;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;

import static com.github.foobar27.datamapper.boilerplate.PersonSchema.personSchema;

public class PersonFactory implements EntityFactory<ImmutablePerson, ImmutablePerson.Builder> {

    private static final PersonFactory instance = new PersonFactory();

    public static PersonFactory getInstance() {
        return instance;
    }

    @Override
    public EntitySchema getSchema() {
        return personSchema();
    }

    @Override
    public ImmutablePerson.Builder newBuilder() {
        return ImmutablePerson.newBuilder();
    }

    @Override
    public ImmutablePerson.Builder newBuilder(EntityFieldReadAccessor entity) {
        return ImmutablePerson.newBuilder(entity);
    }

    @Override
    public ImmutablePerson.Builder toBuilder(ImmutablePerson entity) {
        return entity.toBuilder();
    }

    @Override
    public ImmutablePerson build(ImmutablePerson.Builder builder) {
        return builder.build();
    }
}
