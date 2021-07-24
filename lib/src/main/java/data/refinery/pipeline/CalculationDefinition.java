package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntitySchema;

public interface CalculationDefinition {

    EntitySchema getInputSchema();

    EntitySchema getParameterSchema();

    EntitySchema getOutputSchema();

    default CalculationDefinition wrap(ProfunctorEntityMapping mapping) {
        return WrappedCalculationDefinition.wrap(this, mapping);
    }

}
