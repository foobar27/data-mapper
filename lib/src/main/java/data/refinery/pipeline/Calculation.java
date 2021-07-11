package data.refinery.pipeline;

import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntitySchema;

public interface Calculation {

    EntitySchema getInputSchema();

    EntitySchema getParameterSchema();

    EntitySchema getOutputSchema();

    default Calculation wrap(ProfunctorEntityMapping mapping) {
        return WrappedCalculation.wrap(this, mapping);
    }

}
