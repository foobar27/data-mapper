package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.*;

class AppendConstantCalculation implements Calculation {

    static final class InputSchema extends FluentEntitySchema {

        private static final InputSchema instance = new InputSchema();

        private final Field value = register("value");

        private InputSchema() {
            super("AppendConstantCalculation.input");
        }

        Field value() {
            return value;
        }

    }

    static final class ParameterSchema extends FluentEntitySchema {

        private static final ParameterSchema instance = new ParameterSchema();

        private final Field constant = register("constant");

        private ParameterSchema() {
            super("AppendConstantCalculation.parameter");
        }

        Field constant() {
            return constant;
        }

    }

    static final class OutputSchema extends FluentEntitySchema {

        private static final OutputSchema instance = new OutputSchema();

        private final Field value = register("value");

        private OutputSchema() {
            super("AppendConstantCalculation.output");
        }

        Field value() {
            return value;
        }

    }

    static InputSchema inputSchema() {
        return InputSchema.instance;
    }

    static ParameterSchema parameterSchema() {
        return ParameterSchema.instance;
    }

    static OutputSchema outputSchema() {
        return OutputSchema.instance;
    }

    private static final AppendConstantCalculation instance = new AppendConstantCalculation();

    public static AppendConstantCalculation getInstance() {
        return instance;
    }

    private AppendConstantCalculation() {
        // inhibit public constructor
    }

    @Override
    public EntitySchema getInputSchema() {
        return inputSchema();
    }

    @Override
    public EntitySchema getParameterSchema() {
        return parameterSchema();
    }

    @Override
    public EntitySchema getOutputSchema() {
        return outputSchema();
    }
}
