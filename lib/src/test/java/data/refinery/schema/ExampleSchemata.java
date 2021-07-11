package data.refinery.schema;

import com.google.common.collect.ImmutableList;

public class ExampleSchemata {

    public static final Field personFirstName = new NamedField("person.firstName");
    public static final Field personLastName = new NamedField("person.lastName");
    public static final Field personFullName = new NamedField("person.fullName");
    public static final Field personAge = new NamedField("person.age");
    public static final Field unusedField = new NamedField("unused");
    public static final NamedEntitySchema person = new NamedEntitySchema(
            "Person",
            ImmutableList.of(personFirstName, personLastName, personFullName, personAge));

}
