package data.refinery.boilerplate;

import data.refinery.conversion.EntityFactory;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntitySchema;

import static data.refinery.boilerplate.PersonSchema.personSchema;

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
