package com.github.foobar27.datamapper.mapping;

import com.google.common.collect.ImmutableList;
import com.github.foobar27.datamapper.schema.*;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EntityMappingViewTest {

    @Test
    public void viewAccessors() {
        NamedField fieldA1 = new NamedField("A1");
        NamedField fieldA2 = new NamedField("A2");
        EntitySchema schemaA = new NamedEntitySchema("A", ImmutableList.of(fieldA1, fieldA2));

        NamedField fieldB1 = new NamedField("B1");
        NamedField fieldB2 = new NamedField("B2");
        NamedField fieldB3 = new NamedField("B3");
        EntitySchema schemaB = new NamedEntitySchema("B", ImmutableList.of(fieldB1, fieldB2, fieldB3));

        EntityMapping mapping = ImmutableEntityMapping.newBuilder(schemaA, schemaB)
                .mapField(fieldA1, fieldB1)
                .mapField(fieldA2, fieldB2)
                .build();

        SimpleEntity entityA = new SimpleEntity(schemaA);
        entityA.setValueOfField(fieldA1, "A1");
        entityA.setValueOfField(fieldA2, "A2");

        SimpleEntity entityB = new SimpleEntity(schemaB);
        EntityMappingView view = mapping.createView(entityA);
        assertThat(view.getSchema().getFields(), is(ImmutableList.of(fieldB1, fieldB2)));
        view.mergeInto(entityB);

        assertThat(entityB.getValueOfField(fieldB1), is("A1"));
        assertThat(entityB.getValueOfField(fieldB2), is("A2"));
    }

}
