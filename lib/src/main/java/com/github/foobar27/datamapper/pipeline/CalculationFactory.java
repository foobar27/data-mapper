package com.github.foobar27.datamapper.pipeline;

import java.util.function.Function;

public interface CalculationFactory extends Function<CalculationDefinition, CalculationImplementation> {
}
