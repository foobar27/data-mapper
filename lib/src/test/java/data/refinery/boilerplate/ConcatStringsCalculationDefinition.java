package data.refinery.boilerplate;

import data.refinery.conversion.EntityFactory;
import data.refinery.pipeline.CalculationDefinition;
import data.refinery.schema.NoSuchFieldException;
import data.refinery.schema.*;

import static com.google.common.base.Preconditions.checkArgument;

public class ConcatStringsCalculationDefinition implements CalculationDefinition {

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

    public interface InputReadAccessors extends EntityFieldReadAccessor {

        String getLeft();

        String getRight();

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

        @Override
        default Object getValueOfField(Field field) {
            if (field == InputSchema.instance.left) {
                return getLeft();
            } else if (field == InputSchema.instance.right) {
                return getRight();
            }
            throw new NoSuchFieldException(getSchema(), field);
        }

    }

    public interface InputWriteAccessors extends EntityFieldWriteAccessor {

        void setLeft(String value);

        void setRight(String value);

        @Override
        default void setValueOfField(Field field, Object value) {
            if (field == inputSchema().left) {
                setLeft((String) value);
            } else if (field == inputSchema().right) {
                setLeft((String) value);
            } else {
                throw new NoSuchFieldException(getSchema(), field);
            }
        }

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

    }

    public interface InputReadWriteAccessors extends InputReadAccessors, InputWriteAccessors, EntityFieldReadWriteAccessor {

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

    }

    public static final class ImmutableInput implements InputReadAccessors {

        private final String left;
        private final String right;

        private ImmutableInput(Builder builder) {
            this.left = builder.left;
            this.right = builder.right;
        }

        @Override
        public String getLeft() {
            return left;
        }

        @Override
        public String getRight() {
            return right;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(EntityFieldReadAccessor input) {
            checkArgument(input.getSchema() == inputSchema());
            Builder builder = new Builder();
            builder.setLeft((String) input.getValueOfField(inputSchema().left()));
            builder.setRight((String) input.getValueOfField(inputSchema().right()));
            return builder;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder implements InputReadWriteAccessors {
            private String left;
            private String right;

            private Builder() {
                // nothing to do
            }

            private Builder(ImmutableInput input) {
                this.left = input.left;
                this.right = input.right;
            }

            @Override
            public String getLeft() {
                return left;
            }

            @Override
            public String getRight() {
                return right;
            }

            @Override
            public void setLeft(String value) {
                this.left = value;
            }

            @Override
            public void setRight(String value) {
                this.right = value;
            }

            public ImmutableInput build() {
                return new ImmutableInput(this);
            }
        }

    }

    public static final class InputFactory implements EntityFactory<ImmutableInput, ImmutableInput.Builder> {

        @Override
        public InputSchema getSchema() {
            return InputSchema.instance;
        }

        @Override
        public ImmutableInput.Builder newBuilder() {
            return ImmutableInput.newBuilder();
        }

        @Override
        public ImmutableInput.Builder newBuilder(EntityFieldReadAccessor entity) {
            return ImmutableInput.newBuilder(entity);
        }

        @Override
        public ImmutableInput.Builder toBuilder(ImmutableInput entity) {
            return entity.toBuilder();
        }

        @Override
        public ImmutableInput build(ImmutableInput.Builder builder) {
            return builder.build();
        }

    }

    public static final class ParametersSchema extends FluentEntitySchema {
        private static final ParametersSchema instance = new ParametersSchema();

        private final Field middle = register("middle");

        private ParametersSchema() {
            super("ConcatStringsCalculation.parameters");
        }

        public Field middle() {
            return middle;
        }

    }

    public interface ParametersReadAccessors extends EntityFieldReadAccessor {

        String getMiddle();

        @Override
        default ParametersSchema getSchema() {
            return ParametersSchema.instance;
        }

        @Override
        default Object getValueOfField(Field field) {
            if (field == ParametersSchema.instance.middle()) {
                return getMiddle();
            }
            throw new NoSuchFieldException(getSchema(), field);
        }

    }

    public interface ParametersWriteAccessors extends EntityFieldWriteAccessor {

        void setMiddle(String value);

        @Override
        default ParametersSchema getSchema() {
            return ParametersSchema.instance;
        }

        @Override
        default void setValueOfField(Field field, Object value) {
            if (field == ParametersSchema.instance.middle()) {
                setMiddle((String) value);
            } else {
                throw new NoSuchFieldException(getSchema(), field);
            }
        }

    }

    public interface ParametersReadWriteAccessors extends ParametersReadAccessors, ParametersWriteAccessors, EntityFieldReadWriteAccessor {

        @Override
        default ParametersSchema getSchema() {
            return ParametersSchema.instance;
        }

    }

    public static final class ImmutableParameters implements ParametersReadAccessors {
        private final String middle;

        private ImmutableParameters(Builder builder) {
            this.middle = builder.middle;
        }

        @Override
        public String getMiddle() {
            return middle;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(EntityFieldReadAccessor parameters) {
            checkArgument(parameters.getSchema() == ParametersSchema.instance);
            Builder builder = newBuilder();
            builder.setMiddle((String) parameters.getValueOfField(ParametersSchema.instance.middle()));
            return builder;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder implements ParametersReadWriteAccessors {

            private String middle;

            private Builder() {
                // nothing to do
            }

            private Builder(ImmutableParameters parameters) {
                this.middle = parameters.middle;
            }

            @Override
            public String getMiddle() {
                return middle;
            }

            @Override
            public void setMiddle(String value) {
                this.middle = value;
            }

            public ImmutableParameters build() {
                return new ImmutableParameters(this);
            }
        }

    }

    public static final class ParametersFactory implements EntityFactory<ImmutableParameters, ImmutableParameters.Builder> {

        @Override
        public EntitySchema getSchema() {
            return ParametersSchema.instance;
        }

        @Override
        public ImmutableParameters.Builder newBuilder() {
            return ImmutableParameters.newBuilder();
        }

        @Override
        public ImmutableParameters.Builder newBuilder(EntityFieldReadAccessor entity) {
            return ImmutableParameters.newBuilder(entity);
        }

        @Override
        public ImmutableParameters.Builder toBuilder(ImmutableParameters entity) {
            return entity.toBuilder();
        }

        @Override
        public ImmutableParameters build(ImmutableParameters.Builder builder) {
            return builder.build();
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

    public interface OutputReadAccessors extends EntityFieldReadAccessor {

        String getValue();

        @Override
        default OutputSchema getSchema() {
            return OutputSchema.instance;
        }

        @Override
        default Object getValueOfField(Field field) {
            if (field == outputSchema().value) {
                return getValue();
            } else {
                throw new NoSuchFieldException(getSchema(), field);
            }
        }

    }

    public interface OutputWriteAccessors extends EntityFieldWriteAccessor {

        void setValue(String value);

        @Override
        default OutputSchema getSchema() {
            return OutputSchema.instance;
        }

        @Override
        default void setValueOfField(Field field, Object value) {
            if (field == outputSchema().value) {
                setValue((String) value);
            } else {
                throw new NoSuchFieldException(getSchema(), field);
            }
        }
    }

    public interface OutputReadWriteAccessors extends OutputReadAccessors, OutputWriteAccessors, EntityFieldReadWriteAccessor {

        @Override
        default OutputSchema getSchema() {
            return OutputSchema.instance;
        }

    }

    public static final class ImmutableOutput implements OutputReadAccessors {

        private final String value;

        private ImmutableOutput(Builder builder) {
            this.value = builder.value;
        }

        @Override
        public String getValue() {
            return value;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(EntityFieldReadAccessor output) {
            checkArgument(output.getSchema() == outputSchema());
            Builder builder = newBuilder();
            builder.setValue((String) output.getValueOfField(outputSchema().value()));
            return builder;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder implements OutputReadWriteAccessors {
            private String value;

            private Builder() {
                // nothing to do
            }

            private Builder(ImmutableOutput output) {
                this.value = output.value;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                this.value = value;
            }

            public ImmutableOutput build() {
                return new ImmutableOutput(this);
            }
        }

    }

    public static final class OutputFactory implements EntityFactory<ImmutableOutput, ImmutableOutput.Builder> {

        @Override
        public EntitySchema getSchema() {
            return outputSchema();
        }

        @Override
        public ImmutableOutput.Builder newBuilder() {
            return ImmutableOutput.newBuilder();
        }

        @Override
        public ImmutableOutput.Builder newBuilder(EntityFieldReadAccessor entity) {
            return ImmutableOutput.newBuilder(entity);
        }

        @Override
        public ImmutableOutput.Builder toBuilder(ImmutableOutput entity) {
            return entity.toBuilder();
        }

        @Override
        public ImmutableOutput build(ImmutableOutput.Builder builder) {
            return builder.build();
        }
    }


    public static InputSchema inputSchema() {
        return InputSchema.instance;
    }

    public static ParametersSchema parameterSchema() {
        return ParametersSchema.instance;
    }

    public static OutputSchema outputSchema() {
        return OutputSchema.instance;
    }

    private static final ConcatStringsCalculationDefinition instance = new ConcatStringsCalculationDefinition();

    public static ConcatStringsCalculationDefinition getInstance() {
        return instance;
    }

    private ConcatStringsCalculationDefinition() {
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
