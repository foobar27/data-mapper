package data.mapper.schema;

import java.util.HashMap;
import java.util.Map;

// TODO ideally would not even have a schema?
public class SimpleEntity<Schema extends EntitySchema> implements EntityReadWriteAccessor<Schema> {

    private final Map<Field<?>, Object> values = new HashMap<>();

    @Override
    public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
        return (NativeType) values.get(field);
    }

    @Override
    public <NativeType, FieldType extends Field<NativeType>> void setValueOfField(FieldType field, NativeType value) {
        values.put(field, value);
    }
}
