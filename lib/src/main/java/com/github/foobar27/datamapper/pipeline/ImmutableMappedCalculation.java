package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.mapping.EntityAdapter;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableMappedCalculation implements MappedCalculation {

    private final CalculationDefinition calculationDefinition;
    private final EntityAdapter mapping;

    public ImmutableMappedCalculation(CalculationDefinition calculationDefinition, EntityAdapter mapping) {
        this.calculationDefinition = checkNotNull(calculationDefinition);
        this.mapping = checkNotNull(mapping);
    }

    @Override
    public CalculationDefinition getCalculation() {
        return calculationDefinition;
    }

    @Override
    public EntityAdapter getMapping() {
        return mapping;
    }

}
