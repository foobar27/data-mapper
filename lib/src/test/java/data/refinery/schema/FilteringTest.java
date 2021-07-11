package data.refinery.schema;

import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static data.refinery.schema.ExampleSchemata.personAge;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilteringTest {

    @Test
    public void accessorShouldFailOnRemoveField() {
        Set<Field> nameFields = ImmutableSet.of(ExampleSchemata.personFirstName);
        SimpleEntity entity = new SimpleEntity(ExampleSchemata.person);
        EntityFieldReadAccessor filteredEntity = entity.filterFields(nameFields);
        assertThrows(NoSuchFieldException.class, () -> filteredEntity.getValueOfField(personAge));
    }

    @Test
    public void removeNoFieldsShouldTriggerFastPath() {
        SimpleEntity entity = new SimpleEntity(ExampleSchemata.person);
        EntityFieldReadAccessor filteredEntity = entity.filterFields(Collections.emptySet());
        assertThat(filteredEntity, instanceOf(SimpleEntity.class));
    }

    @Test
    public void removeAllFieldsShouldTriggerFastPath() {
        SimpleEntity entity = new SimpleEntity(ExampleSchemata.person);
        EntityFieldReadAccessor filteredEntity = entity.filterFields(new HashSet<>(entity.getSchema().getFields()));
        assertThat(filteredEntity, instanceOf(EmptyEntityFieldReadAccessor.class));
    }

    // TODO verify schema also filtered
    // TODO verify schemata match
    // TODO verify fast path for double filtering

}
