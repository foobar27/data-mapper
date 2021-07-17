package data.refinery.schema;

import org.junit.jupiter.api.Test;

import static data.refinery.schema.ExampleSchemata.personFirstName;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

public class SimpleEntityTest {

    @Test
    public void accessorsShouldWorkAsExpected() {
        SimpleEntity entity = new SimpleEntity(ExampleSchemata.person);
        assertThat(entity.getValueOfField(personFirstName), is(equalTo(null)));
        entity.setValueOfField(personFirstName, "John");
        assertThat(entity.getValueOfField(personFirstName), is("John"));
        assertThat(entity.toString(), is("SimpleEntity{schema=NamedEntitySchema{name=Person, fields=[Field[person.firstName], Field[person.lastName], Field[person.fullName], Field[person.age]]}, Field[person.firstName]=John}"));
        assertThrows(NoSuchFieldException.class, () -> entity.getValueOfField(ExampleSchemata.unusedField));
    }

    // TODO test hashCode / equals

}
