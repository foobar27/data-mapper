package data.refinery.schema;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static data.refinery.schema.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilteringTest {

    private final List<Field> allNameFields = ImmutableList.of(
            personSchema().firstName(),
            personSchema().lastName(),
            personSchema().fullName());

    @Test
    public void accessorShouldFailOnRemovedField() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        EntityFieldReadAccessor filteredEntity = entity.filterFields(ImmutableSet.copyOf(allNameFields));
        assertThrows(NoSuchFieldException.class, () -> filteredEntity.getValueOfField(personSchema().age()));
    }

    @Test
    public void filteredEntityShouldHaveAdjustedSchema() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        EntityFieldReadAccessor filteredEntity = entity.filterFields(ImmutableSet.copyOf(allNameFields));
        assertThat(filteredEntity.getSchema().getFields(), equalTo(allNameFields));
    }

    @Test
    public void doubleFilteringShouldTriggerFastPath() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        EntityFieldReadAccessor filteredEntity1 = entity.filterFields(ImmutableSet.copyOf(allNameFields));
        assertThat(filteredEntity1.getSchema().getFields(), equalTo(allNameFields));

        List<Field> realNameFields = ImmutableList.of(
                personSchema().firstName(),
                personSchema().lastName());
        EntityFieldReadAccessor filteredEntity2 = filteredEntity1.filterFields(ImmutableSet.copyOf(realNameFields));
        assertThat(filteredEntity2.getSchema().getFields(), equalTo(realNameFields));
        // Unfortunately the only way to check if the fast path was triggered is to call the toString method.
        // However, I do not want to expose any introspection logic, because this is essentially just a performance optimization.
        assertThat(filteredEntity2.toString(),
                equalTo("FilteredEntityFieldReadAccessor[SimpleEntity{schema=Person}, [Field[Person.firstName], Field[Person.lastName]]]"));
    }

    @Test
    public void removeNoFieldsShouldTriggerFastPath() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        EntityFieldReadAccessor filteredEntity = entity.filterFields(Collections.emptySet());
        assertThat(filteredEntity, instanceOf(EmptyEntityFieldReadAccessor.class));
    }

    @Test
    public void removeAllFieldsShouldTriggerFastPath() {
        SimpleEntity entity = new SimpleEntity(personSchema());
        EntityFieldReadAccessor filteredEntity = entity.filterFields(new HashSet<>(entity.getSchema().getFields()));
        assertThat(filteredEntity, instanceOf(SimpleEntity.class));
    }

}
