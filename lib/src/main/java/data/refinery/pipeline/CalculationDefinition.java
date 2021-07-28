package data.refinery.pipeline;

import data.refinery.mapping.EntityAdapter;
import data.refinery.schema.EntitySchema;

public interface CalculationDefinition {

    EntitySchema getInputSchema();

    EntitySchema getParameterSchema();

    EntitySchema getOutputSchema();

    default CalculationDefinition wrap(EntityAdapter mapping) {
        return WrappedCalculationDefinition.wrap(this, mapping);
    }

}
