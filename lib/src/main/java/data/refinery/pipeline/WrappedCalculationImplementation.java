package data.refinery.pipeline;

import data.refinery.mapping.EntityMappingView;
import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;

import java.util.concurrent.CompletableFuture;

import static com.google.common.base.Preconditions.checkNotNull;

final class WrappedCalculationImplementation implements CalculationImplementation {

    private final CalculationImplementation delegate;
    private final CalculationDefinition calculationDefinition;
    private final ProfunctorEntityMapping mapping;

    public static CalculationImplementation wrapCalculation(CalculationImplementation delegate, ProfunctorEntityMapping mapping) {
        checkNotNull(delegate);
        checkNotNull(mapping);
        // TODO verify schemas match
        // TODO fast path: compose profunctors
        // TODO fast path: if identity
        return new WrappedCalculationImplementation(delegate, mapping);
    }

    private WrappedCalculationImplementation(CalculationImplementation delegate, ProfunctorEntityMapping mapping) {
        this.delegate = delegate;
        this.mapping = mapping;
        this.calculationDefinition = delegate.getCalculation().wrap(mapping);
    }

    @Override
    public CalculationDefinition getCalculation() {
        return calculationDefinition;
    }

    @Override
    public CompletableFuture<EntityFieldReadAccessor> apply(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters) {
        EntityMappingView wrappedInput = mapping.getLeftMapping().createView(input);
        return delegate.apply(wrappedInput, parameters)
                .thenApply(output -> mapping.getRightMapping().createView(output)); // TODO add executor
    }

}
