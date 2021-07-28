package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableEnrichment implements Enrichment {

    private final MappedCalculation mappedCalculation;
    private final EntityFieldReadAccessor parameters;

    public ImmutableEnrichment(MappedCalculation mappedCalculation, EntityFieldReadAccessor parameters) {
        this.mappedCalculation = checkNotNull(mappedCalculation);
        this.parameters = checkNotNull(parameters);
        checkArgument(getMappedCalculation().getCalculation().getParameterSchema().equals(parameters.getSchema()));
        // TODO verify schema matches
    }

    @Override
    public MappedCalculation getMappedCalculation() {
        return mappedCalculation;
    }

    @Override
    public EntityFieldReadAccessor getParameters() {
        return parameters;
    }

}
