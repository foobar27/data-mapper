package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableMappedCalculation implements MappedCalculation {

    private final Calculation calculation;
    private final ProfunctorEntityMapping mapping;

    public ImmutableMappedCalculation(Calculation calculation, ProfunctorEntityMapping mapping) {
        this.calculation = checkNotNull(calculation);
        this.mapping = checkNotNull(mapping);
    }

    @Override
    public Calculation getCalculation() {
        return calculation;
    }

    @Override
    public ProfunctorEntityMapping getMapping() {
        return mapping;
    }

}
