package data.refinery.mapping;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldWriteAccessor;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

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
