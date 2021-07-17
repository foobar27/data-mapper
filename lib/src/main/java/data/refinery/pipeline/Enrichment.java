package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

public interface Enrichment {

    MappedCalculation getMappedCalculation();

    EntityFieldReadAccessor getParameters();

}
