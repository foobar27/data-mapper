package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;

import static data.refinery.pipeline.AppendConstantCalculation.inputValue;
import static data.refinery.pipeline.AppendConstantCalculation.parameterConstant;
import static data.refinery.pipeline.AppendConstantCalculation.outputValue;

public class AppendConstantCalculationImplementation extends TestableCalculationImplementation {

    public AppendConstantCalculationImplementation enableAutoApply() {
        getPendingFutures().enableAutoApply();
        return this;
    }

    @Override
    public Calculation getCalculation() {
        return AppendConstantCalculation.getInstance();
    }

    @Override
    protected EntityFieldReadAccessor calculate(EntityFieldReadAccessor input, EntityFieldReadAccessor parameters) {
        String value = (String) input.getValueOfField(inputValue);
        String constant = (String) parameters.getValueOfField(parameterConstant);
        String result = value + constant;
        SimpleEntity output = new SimpleEntity(getCalculation().getOutputSchema());
        output.setValueOfField(outputValue, result);
        return output;
    }
}
