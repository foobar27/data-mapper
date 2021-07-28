package data.refinery.pipeline;

import data.refinery.mapping.EntityAdapter;
import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public interface CalculationImplementation extends BiFunction<EntityFieldReadAccessor, EntityFieldReadAccessor, CompletableFuture<EntityFieldReadAccessor>> {

    CalculationDefinition getCalculation();

    default CalculationImplementation wrap(EntityAdapter mapping) {
        return WrappedCalculationImplementation.wrapCalculation(this, mapping);
    }

}
