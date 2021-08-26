package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.mapping.EntityAdapter;

public interface MappedCalculation {

    CalculationDefinition getCalculation();

    /**
     * Gets the mapping from the relevant subset of the entity to the input of the calculation,
     * and from the output of the calculation to the relevant subset of the entity.
     */
    EntityAdapter getMapping();

}
