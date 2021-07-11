package data.mapper.pipeline;

import data.mapper.schema.EntitySchema;
import data.mapper.schema.Field;

import java.util.HashMap;
import java.util.Map;

public class AbstractEntityWithStrategies<Schema extends EntitySchema> implements EntityWithStrategies {

    private final Map<Field<?>, ValueOrStrategy<?>> fields = new HashMap<>();

    public <NativeType, FieldType extends Field<NativeType>> ValueOrStrategy<NativeType> registerField(FieldType field) {
        ValueOrStrategy<NativeType> result = new ValueOrStrategy<>();
        fields.put(field, result);
        return result;
    }

    @Override
    public Map<Field<?>, ValueOrStrategy<?>> getAllFields() {
        return fields;
    }

}
