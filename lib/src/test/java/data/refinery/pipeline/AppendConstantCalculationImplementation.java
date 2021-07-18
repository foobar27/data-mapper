package data.refinery.pipeline;

import data.refinery.boilerplate.AppendConstantCalculation;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;

import java.util.concurrent.Executor;

import static data.refinery.boilerplate.AppendConstantCalculation.*;

public class AppendConstantCalculationImplementation extends TestableCalculationImplementation {

    public AppendConstantCalculationImplementation(Executor executor) {
        super(executor);
    }

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
        String value = (String) input.getValueOfField(inputSchema().value());
        String constant = (String) parameters.getValueOfField(parameterSchema().constant());
        String result = value + constant;
        SimpleEntity output = new SimpleEntity(getCalculation().getOutputSchema());
        output.setValueOfField(outputSchema().value(), result);
        return output;
    }
}
