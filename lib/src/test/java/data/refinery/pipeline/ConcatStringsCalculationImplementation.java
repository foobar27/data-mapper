package data.refinery.pipeline;


import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;

import java.util.concurrent.Executor;

import static data.refinery.pipeline.ConcatStringsCalculation.*;


public class ConcatStringsCalculationImplementation extends TestableCalculationImplementation {

    public ConcatStringsCalculationImplementation(Executor executor) {
        super(executor);
    }

    public ConcatStringsCalculationImplementation enableAutoApply() {
        getPendingFutures().enableAutoApply();
        return this;
    }

    @Override
    public Calculation getCalculation() {
        return ConcatStringsCalculation.getInstance();
    }

    @Override
    protected EntityFieldReadAccessor calculate(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters) {
        String left = (String) input.getValueOfField(inputSchema().left());
        String right = (String) input.getValueOfField(inputSchema().right());

        String middle = (String) parameters.getValueOfField(parameterSchema().middle());

        String result = left + middle + right;
        SimpleEntity output = new SimpleEntity(getCalculation().getOutputSchema());
        output.setValueOfField(outputSchema().value(), result);
        return output;
    }
}
