package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.*;

import java.lang.reflect.Parameter;

public class ConcatStringsCalculation implements Calculation {

    public static final class InputSchema extends FluentEntitySchema {

        private static final InputSchema instance = new InputSchema();

        private final Field left = register("left");
        private final Field right = register("right");

        private InputSchema() {
            super("ConcatStringsCalculation.input");
        }

        public Field left() {
            return left;
        }

        public Field right() {
            return right;
        }
    }

    public static final class ParameterSchema extends FluentEntitySchema {
        private static final ParameterSchema instance = new ParameterSchema();

        private final Field middle = register("middle");

        private ParameterSchema(){
            super("ConcatStringsCalculation.parameters");
        }

        public Field middle() {
            return middle;
        }

    }

    public static final class OutputSchema extends FluentEntitySchema {
        private static final OutputSchema instance = new OutputSchema();

        private final Field value = register("value");

        private OutputSchema() {
            super("ConcatStringsCalculation.output");
        }

        public Field value() {
            return value;
        }
    }

    public static InputSchema inputSchema() {
        return InputSchema.instance;
    }

    public static ParameterSchema parameterSchema() {
        return ParameterSchema.instance;
    }

    public static OutputSchema outputSchema() {
        return OutputSchema.instance;
    }

    private static final ConcatStringsCalculation instance = new ConcatStringsCalculation();

    public static ConcatStringsCalculation getInstance() {
        return instance;
    }

    private ConcatStringsCalculation() {
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
