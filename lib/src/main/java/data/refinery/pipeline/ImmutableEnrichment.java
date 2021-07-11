package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableEnrichment implements Enrichment {

    private final Calculation calculation;
    private final EntityFieldReadAccessor parameters;
    private final ProfunctorEntityMapping mapping;

    public ImmutableEnrichment(Calculation calculation, EntityFieldReadAccessor parameters, ProfunctorEntityMapping mapping) {
        this.calculation = checkNotNull(calculation);
        this.parameters = checkNotNull(parameters);
        this.mapping = checkNotNull(mapping);
        checkArgument(calculation.getParameterSchema().equals(parameters.getSchema()));
        // TODO verify schema matches
    }

    @Override
    public Calculation getCalculation() {
        return calculation;
    }

    @Override
    public EntityFieldReadAccessor getParameters() {
        return parameters;
    }

    @Override
    public ProfunctorEntityMapping getMapping() {
        return mapping;
    }
}
