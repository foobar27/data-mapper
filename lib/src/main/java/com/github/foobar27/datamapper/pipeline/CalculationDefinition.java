package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.mapping.EntityAdapter;
import com.github.foobar27.datamapper.schema.EntitySchema;

public interface CalculationDefinition {

    EntitySchema getInputSchema();

    EntitySchema getParameterSchema();

    EntitySchema getOutputSchema();

    default CalculationDefinition wrap(EntityAdapter mapping) {
        return WrappedCalculationDefinition.wrap(this, mapping);
    }

}
