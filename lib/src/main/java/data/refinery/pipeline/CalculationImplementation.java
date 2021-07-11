package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public interface CalculationImplementation extends BiFunction<EntityFieldReadAccessor, EntityFieldReadAccessor, CompletableFuture<EntityFieldReadAccessor>> {

    Calculation getCalculation();

    default CalculationImplementation wrap(ProfunctorEntityMapping mapping) {
        return WrappedCalculationImplementation.wrapCalculation(this, mapping);
    }

}
