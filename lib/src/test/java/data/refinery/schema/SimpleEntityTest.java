package data.refinery.schema;

import org.junit.jupiter.api.Test;

import static data.refinery.schema.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleEntityTest {

    @Test
    public void accessorsShouldWorkAsExpected() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        assertThat(entity.getValueOfField(personSchema().firstName()), is(equalTo(null)));
        entity.setValueOfField(personSchema().firstName(), "John");
        assertThat(entity.getValueOfField(personSchema().firstName()), is("John"));
        assertThat(entity.toString(), is("SimpleEntity{schema=Person, Field[Person.firstName]=John}"));
        assertThrows(NoSuchFieldException.class, () -> entity.getValueOfField(new NamedField("unusedField")));
    }

    // TODO test hashCode / equals

}
