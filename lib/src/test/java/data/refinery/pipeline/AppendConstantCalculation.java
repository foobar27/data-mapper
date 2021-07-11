package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;
import data.refinery.schema.NamedEntitySchema;
import data.refinery.schema.NamedField;

class AppendConstantCalculation implements Calculation {

    static final Field inputValue = new NamedField("AppendConstantCalculation.input.value");
    static final EntitySchema inputSchema = new NamedEntitySchema(
            "AppendConstantCalculation.input",
            ImmutableList.of(inputValue));

    static final Field parameterConstant = new NamedField("AppendConstantCalculation.parameters.constant");
    static final EntitySchema parameterSchema = new NamedEntitySchema(
            "AppendConstantCalculation.parameter",
            ImmutableList.of(parameterConstant));

    static final Field outputValue = new NamedField("AppendConstantCalculation.output.value");
    static final EntitySchema outputSchema = new NamedEntitySchema(
            "AppendConstantCalculation.output",
            ImmutableList.of(outputValue));

    private static final AppendConstantCalculation instance = new AppendConstantCalculation();

    public static AppendConstantCalculation getInstance() {
        return instance;
    }

    private AppendConstantCalculation() {
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
