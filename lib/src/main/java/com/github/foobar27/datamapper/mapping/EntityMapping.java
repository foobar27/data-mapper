package com.github.foobar27.datamapper.mapping;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldWriteAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;
import com.github.foobar27.datamapper.schema.Field;

import java.util.Map;

public interface EntityMapping {

    EntitySchema getInputSchema();

    EntitySchema getOutputSchema();

    Map<Field, Field> getMapping();

    Map<Field, Field> getReverseMapping();

    default EntityMapping reverse() {
        return ReversedEntityMapping.reverse(this);
    }

    default void map(EntityFieldReadAccessor input, EntityFieldWriteAccessor output) {
        getMapping().forEach((inputField, outputField) ->
                output.setValueOfField(outputField, input.getValueOfField(inputField)));
    }

    default EntityMappingView createView(EntityFieldReadAccessor input) {
        return new EntityMappingView(input, this);
    }

}
