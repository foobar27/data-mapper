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
        String left = (String) input.getValueOfField(inputLeft);
        String right = (String) input.getValueOfField(inputRight);

        String middle = (String) parameters.getValueOfField(parametersMiddle);

        String result = left + middle + right;
        SimpleEntity output = new SimpleEntity(getCalculation().getOutputSchema());
        output.setValueOfField(outputValue, result);
        return output;
    }
}
