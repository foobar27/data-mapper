package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableMappedCalculation implements MappedCalculation {

    private final CalculationDefinition calculationDefinition;
    private final ProfunctorEntityMapping mapping;

    public ImmutableMappedCalculation(CalculationDefinition calculationDefinition, ProfunctorEntityMapping mapping) {
        this.calculationDefinition = checkNotNull(calculationDefinition);
        this.mapping = checkNotNull(mapping);
    }

    @Override
    public CalculationDefinition getCalculation() {
        return calculationDefinition;
    }

    @Override
    public ProfunctorEntityMapping getMapping() {
        return mapping;
    }

}
