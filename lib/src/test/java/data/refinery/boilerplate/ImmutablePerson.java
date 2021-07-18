package data.refinery.boilerplate;

import data.refinery.schema.EntityFieldReadAccessor;

import static data.refinery.boilerplate.PersonSchema.personSchema;

public final class ImmutablePerson implements PersonReadAccessors {

    private final String firstName;
    private final String lastName;
    private final String fullName;
    private final int age;

    private ImmutablePerson(Builder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.fullName = builder.fullName;
        this.age = builder.age;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int getAge() {
        return age;
    }

    // TODO hashCode/equals/toString

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EntityFieldReadAccessor person) {
        if (person.getSchema() != personSchema()) {
            throw new IllegalArgumentException("Trying to assign " + person.getSchema() + " to an ImmutablePerson.Builder");
        }
        Builder builder = newBuilder();
        builder.firstName = (String) person.getValueOfField(personSchema().firstName());
        builder.lastName = (String) person.getValueOfField(personSchema().lastName());
        builder.fullName =  (String) person.getValueOfField(personSchema().fullName());
        builder.age = (int) person.getValueOfField(personSchema().age()) ;
        return builder;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder implements PersonReadWriteAccessors {
        private String firstName;
        private String lastName;
        private String fullName;
        private int age;

        private Builder() {
            // nothing to do
        }

        private Builder(ImmutablePerson person) {
            this.firstName = person.firstName;
            this.lastName = person.lastName;
            this.fullName = person.fullName;
            this.age = person.age;
        }

        public ImmutablePerson build() {
            return new ImmutablePerson(this);
        }

        @Override
        public String getFirstName() {
            return firstName;
        }

        @Override
        public String getLastName() {
            return lastName;
        }

        @Override
        public String getFullName() {
            return fullName;
        }

        @Override
        public int getAge() {
            return age;
        }

        @Override
        public void setFirstName(String value) {
            this.firstName = value;
        }

        @Override
        public void setLastName(String value) {
            this.lastName = value;
        }

        @Override
        public void setFullName(String value) {
            this.fullName = value;
        }

        @Override
        public void setAge(int value) {
            this.age = value;
        }

        // TODO hashCode/equals/toString
    }

}
