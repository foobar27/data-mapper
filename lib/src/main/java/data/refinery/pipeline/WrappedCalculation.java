package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntitySchema;

import static com.google.common.base.Preconditions.checkNotNull;

final class WrappedCalculation implements Calculation {

    private final Calculation delegate;
    private final ProfunctorEntityMapping mapping;

    static WrappedCalculation wrap(Calculation delegate, ProfunctorEntityMapping mapping) {
        checkNotNull(delegate);
        checkNotNull(mapping);
        // TODO verify schemas match
        // TODO fast path: compose profunctors
        // TODO fast path: if identity mapping
        return new WrappedCalculation(delegate, mapping);
    }

    private WrappedCalculation(Calculation delegate, ProfunctorEntityMapping mapping) {
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
