package com.github.foobar27.datamapper.pipeline;


import com.github.foobar27.datamapper.boilerplate.ConcatStringsCalculationDefinition;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.SimpleEntity;

import java.util.concurrent.Executor;

import static com.github.foobar27.datamapper.boilerplate.ConcatStringsCalculationDefinition.*;


public class ConcatStringsCalculationImplementation extends TestableCalculationImplementation {

    public ConcatStringsCalculationImplementation(Executor executor) {
        super(executor);
    }

    public ConcatStringsCalculationImplementation enableAutoApply() {
        getPendingFutures().enableAutoApply();
        return this;
    }

    @Override
    public CalculationDefinition getCalculation() {
        return ConcatStringsCalculationDefinition.getInstance();
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
