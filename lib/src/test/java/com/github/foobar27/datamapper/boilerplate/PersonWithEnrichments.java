package com.github.foobar27.datamapper.boilerplate;

import com.github.foobar27.datamapper.pipeline.AbstractEntityWithEnrichments;
import com.github.foobar27.datamapper.pipeline.TypedValueOrEnrichment;

import static com.github.foobar27.datamapper.boilerplate.PersonSchema.personSchema;

public class PersonWithEnrichments extends AbstractEntityWithEnrichments {

    private final TypedValueOrEnrichment<String> firstName = register(personSchema().firstName());
    private final TypedValueOrEnrichment<String> lastName = register(personSchema().lastName());
    private final TypedValueOrEnrichment<String> fullName = register(personSchema().fullName());
    private final TypedValueOrEnrichment<Integer> age = register(personSchema().age());

    public PersonWithEnrichments() {
        super(personSchema());
    }

    public TypedValueOrEnrichment<String> firstName() {
        return firstName;
    }

    public TypedValueOrEnrichment<String> lastName() {
        return lastName;
    }

    public TypedValueOrEnrichment<String> fullName() {
        return fullName;
    }

    public TypedValueOrEnrichment<Integer> age() {
        return age;
    }

}
