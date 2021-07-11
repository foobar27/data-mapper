package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;
import data.refinery.schema.NamedEntitySchema;
import data.refinery.schema.NamedField;

public class ConcatStringsCalculation implements Calculation {

    static final Field inputLeft = new NamedField("ConcatStringsCalculation.input.left");
    static final Field inputRight = new NamedField("ConcatStringsCalculation.input.right");
    static final EntitySchema inputSchema = new NamedEntitySchema(
            "ConcatStringsCalculation.input",
            ImmutableList.of(inputLeft, inputRight));

    static final Field parametersMiddle = new NamedField("ConcatStringsCalculation.parameters.middle");
    static final EntitySchema parameterSchema = new NamedEntitySchema(
            "ConcatStringsCalculation.parameters",
            ImmutableList.of(parametersMiddle));

    static final Field outputValue = new NamedField("ConcatStringsCalculation.output.value");
    static final EntitySchema outputSchema = new NamedEntitySchema(
            "ConcatStringsCalculation.output",
            ImmutableList.of(outputValue));

    private static final ConcatStringsCalculation instance = new ConcatStringsCalculation();

    public static ConcatStringsCalculation getInstance() {
        return instance;
    }

    private ConcatStringsCalculation() {
        // inhibit public constructor
    }

    @Override
    public EntitySchema getInputSchema() {
        return inputSchema;
    }

    @Override
    public EntitySchema getParameterSchema() {
        return parameterSchema;
    }

    @Override
    public EntitySchema getOutputSchema() {
        return outputSchema;
    }

}
