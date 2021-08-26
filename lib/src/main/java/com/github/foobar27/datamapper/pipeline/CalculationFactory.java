package com.github.foobar27.datamapper.pipeline;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// TODO calculations take parameters (as Entity? eat your own dogfood!)
public final class CalculationFactory implements Function<CalculationDefinition, CalculationImplementation> {

    private final Map<CalculationDefinition, CalculationImplementation> map;

    private CalculationFactory(Builder builder) {
        this.map = ImmutableMap.copyOf(builder.map);
    }

    @Override
    public CalculationImplementation apply(CalculationDefinition calculationDefinition) {
        return map.get(calculationDefinition);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("CalculationFactory")
                .add("map", map)
                .toString();
    }

    public static final class Builder {

        private final Map<CalculationDefinition, CalculationImplementation> map;

        private Builder() {
            this.map = new HashMap<>();
        }

        private Builder(CalculationFactory factory) {
            this.map = new HashMap<>(factory.map);
        }

        public Builder register(CalculationDefinition calculationDefinition, CalculationImplementation implementation) {
            this.map.put(calculationDefinition, implementation);
            return this;
        }

        public CalculationFactory build() {
            return new CalculationFactory(this);
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper("CalculationFactory")
                    .add("map", map)
                    .toString();
        }

    }

}
