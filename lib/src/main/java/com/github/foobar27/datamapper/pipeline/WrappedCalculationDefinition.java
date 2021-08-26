package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.mapping.EntityAdapter;
import com.github.foobar27.datamapper.schema.EntitySchema;

import static com.google.common.base.Preconditions.checkNotNull;

final class WrappedCalculationDefinition implements CalculationDefinition {

    private final CalculationDefinition delegate;
    private final EntityAdapter mapping;

    static WrappedCalculationDefinition wrap(CalculationDefinition delegate, EntityAdapter mapping) {
        checkNotNull(delegate);
        checkNotNull(mapping);
        // TODO verify schemas match
        // TODO fast path: compose adapters
        // TODO fast path: if identity mapping
        return new WrappedCalculationDefinition(delegate, mapping);
    }

    private WrappedCalculationDefinition(CalculationDefinition delegate, EntityAdapter mapping) {
        this.delegate = delegate;
        this.mapping = mapping;
    }

    @Override
    public EntitySchema getInputSchema() {
        return mapping.getLeftMapping().getInputSchema();
    }

    @Override
    public EntitySchema getOutputSchema() {
        return mapping.getRightMapping().getOutputSchema();
    }

    @Override
    public EntitySchema getParameterSchema() {
        return delegate.getParameterSchema();
    }

}
