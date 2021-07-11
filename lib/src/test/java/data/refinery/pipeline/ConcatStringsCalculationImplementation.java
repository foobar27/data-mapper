package data.refinery.pipeline;


import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;

import static data.refinery.pipeline.ConcatStringsCalculation.*;

public class ConcatStringsCalculationImplementation extends TestableCalculationImplementation {

    private static final ConcatStringsCalculationImplementation instance = new ConcatStringsCalculationImplementation();

    public static ConcatStringsCalculationImplementation getInstance() {
        return instance;
    }

    private ConcatStringsCalculationImplementation() {
        // inhibit public constructor
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
