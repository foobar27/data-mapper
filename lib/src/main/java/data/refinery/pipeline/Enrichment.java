package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.EntityFieldReadWriteAccessor;

import java.util.concurrent.CompletableFuture;

public interface Enrichment {

    MappedCalculation getMappedCalculation();

    EntityFieldReadAccessor getParameters();

    default CompletableFuture<EntityFieldReadAccessor> apply(EntityFieldReadWriteAccessor result, CalculationFactory calculationFactory) {
        return calculationFactory.apply(getMappedCalculation().getCalculation())
                .wrap(getMappedCalculation().getMapping())
                .apply(result, getParameters());
    }

}
