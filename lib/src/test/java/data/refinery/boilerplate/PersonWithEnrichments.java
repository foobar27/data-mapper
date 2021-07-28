package data.refinery.boilerplate;

import data.refinery.pipeline.AbstractEntityWithEnrichments;
import data.refinery.pipeline.ValueOrEnrichment;

import static data.refinery.boilerplate.PersonSchema.personSchema;

public class PersonWithEnrichments extends AbstractEntityWithEnrichments {

    private final ValueOrEnrichment firstName = register(personSchema().firstName());
    private final ValueOrEnrichment lastName = register(personSchema().lastName());
    private final ValueOrEnrichment fullName = register(personSchema().fullName());
    private final ValueOrEnrichment age = register(personSchema().age());

    public PersonWithEnrichments() {
        super(personSchema());
    }

    public ValueOrEnrichment firstName() {
        return firstName;
    }

    public ValueOrEnrichment lastName() {
        return lastName;
    }

    public ValueOrEnrichment fullName() {
        return fullName;
    }

    public ValueOrEnrichment age() {
        return age;
    }

}
