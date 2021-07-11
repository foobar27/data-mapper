package data.mapper.pipeline;

import data.mapper.schema.EntityReadWriteAccessor;
import data.mapper.schema.EntitySchema;
import data.mapper.schema.Field;
import data.mapper.schema.SimpleEntity;

public interface Strategy<NativeType, FieldType extends Field<NativeType>, InputSchema extends EntitySchema, OutputSchema extends EntitySchema> {

    InputSchema getInputSchema();

    OutputSchema getOutputFields();

    default EntityReadWriteAccessor<InputSchema> createInputEntity() {
        return new SimpleEntity<>();
    }

}
