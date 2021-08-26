package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.mapping.EntityAdapter;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;

public interface CalculationImplementation extends BiFunction<EntityFieldReadAccessor, EntityFieldReadAccessor, CompletableFuture<EntityFieldReadAccessor>> {

    CalculationDefinition getCalculation();

    default CalculationImplementation wrap(EntityAdapter mapping) {
        return WrappedCalculationImplementation.wrapCalculation(this, mapping);
    }

}
