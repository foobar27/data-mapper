package data.refinery.pipeline;

import data.refinery.conversion.EntityFactory;
import data.refinery.schema.NoSuchFieldException;
import data.refinery.schema.*;

import static com.google.common.base.Preconditions.checkArgument;

public class AppendConstantCalculation implements Calculation {

    public static final class InputSchema extends FluentEntitySchema {

        private static final InputSchema instance = new InputSchema();

        private final Field value = register("value");

        private InputSchema() {
            super("AppendConstantCalculation.input");
        }

        public Field value() {
            return value;
        }

    }

    public interface InputReadAccessors extends EntityFieldReadAccessor {
        String getValue();

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

        @Override
        default Object getValueOfField(Field field) {
            if (field == InputSchema.instance.value) {
                return getValue();
            }
            throw new NoSuchFieldException(getSchema(), field);
        }

    }

    public interface InputWriteAccessors extends EntityFieldWriteAccessor {
        void setValue(String value);

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

        @Override
        default void setValueOfField(Field field, Object value) {
            if (field == InputSchema.instance.value) {
                setValue((String) value);
            } else {
                throw new NoSuchFieldException(getSchema(), field);
            }
        }

    }

    public interface InputReadWriteAccessors extends InputReadAccessors, InputWriteAccessors, EntityFieldReadWriteAccessor {

        @Override
        default InputSchema getSchema() {
            return InputSchema.instance;
        }

    }

    public static final class ImmutableInput implements InputReadAccessors {

        private final String value;

        private ImmutableInput(Builder builder) {
            this.value = builder.value;
        }

        @Override
        public String getValue() {
            return value;
        }

        // TODO toString, hashCode, equals

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(EntityFieldReadAccessor input) {
            checkArgument(input.getSchema() == InputSchema.instance);
            Builder builder = newBuilder();
            builder.setValue((String) input.getValueOfField(InputSchema.instance.value));
            return builder;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder implements InputReadWriteAccessors {

            private String value;

            private Builder() {
                // nothing to do
            }

            private Builder(ImmutableInput input) {
                this.value = input.value;
            }

            @Override
            public String getValue() {
                return value;
            }

            @Override
            public void setValue(String value) {
                this.value = value;
            }

            public ImmutableInput build() {
                return new ImmutableInput(this);
            }

            // TODO toString, hashCode, equals
        }

    }

    public static final class InputFactory implements EntityFactory<ImmutableInput, ImmutableInput.Builder> {

        @Override
        public EntitySchema getSchema() {
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

        private final Field constant = register("constant");

        private ParametersSchema() {
            super("AppendConstantCalculation.parameters");
        }

        public Field constant() {
            return constant;
        }

    }

    public interface ParametersReadAccessors extends EntityFieldReadAccessor {

        String getConstant();

        @Override
        default ParametersSchema getSchema() {
            return ParametersSchema.instance;
        }

        @Override
        default Object getValueOfField(Field field) {
            if (field == ParametersSchema.instance.constant()) {
                return getConstant();
            }
            throw new NoSuchFieldException(getSchema(), field);
        }

    }

    public interface ParametersWriteAccessors extends EntityFieldWriteAccessor {

        void setConstant(String value);

        @Override
        default ParametersSchema getSchema() {
            return ParametersSchema.instance;
        }

        @Override
        default void setValueOfField(Field field, Object value) {
            if (field == ParametersSchema.instance.constant()) {
                setConstant((String) value);
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
        private final String constant;

        private ImmutableParameters(Builder builder) {
            this.constant = builder.constant;
        }

        @Override
        public String getConstant() {
            return constant;
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public static Builder newBuilder(EntityFieldReadAccessor parameters) {
            checkArgument(parameters.getSchema() == ParametersSchema.instance);
            Builder builder = newBuilder();
            builder.setConstant((String) parameters.getValueOfField(ParametersSchema.instance.constant()));
            return builder;
        }

        public Builder toBuilder() {
            return new Builder(this);
        }

        public static final class Builder implements ParametersReadWriteAccessors {

            private String constant;

            private Builder() {
                // nothing to do
            }

            private Builder(ImmutableParameters parameters) {
                this.constant = parameters.constant;
            }

            @Override
            public String getConstant() {
                return constant;
            }

            @Override
            public void setConstant(String value) {
                this.constant = value;
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
            super("AppendConstantCalculation.output");
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

    static InputSchema inputSchema() {
        return InputSchema.instance;
    }

    static ParametersSchema parameterSchema() {
        return ParametersSchema.instance;
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
