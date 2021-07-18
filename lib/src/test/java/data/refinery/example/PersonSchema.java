package data.refinery.example;

import data.refinery.schema.Field;
import data.refinery.schema.FluentEntitySchema;

public class PersonSchema extends FluentEntitySchema {

    private static final PersonSchema instance = new PersonSchema();

    public static PersonSchema personSchema() {
        return instance;
    }

    private final Field firstName = register("firstName");
    private final Field lastName = register("lastName");
    private final Field fullName = register("fullName");
    private final Field age = register("age");

    private PersonSchema() {
        super("Person");
    }

    public Field firstName() {
        return firstName;
    }

    public Field lastName() {
        return lastName;
    }

    public Field fullName() {
        return fullName;
    }

    public Field age() {
        return age;
    }

}
