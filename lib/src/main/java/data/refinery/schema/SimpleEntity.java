package data.refinery.schema;

import com.google.common.base.MoreObjects;

import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;
import static data.refinery.schema.NoSuchFieldException.checkFieldExists;

public final class SimpleEntity implements EntityFieldReadWriteAccessor {

    private final EntitySchema schema;
    private final Map<Field, Object> values = new HashMap<>();

    public SimpleEntity(EntitySchema schema) {
        this.schema = checkNotNull(schema);
    }

    @Override
    public EntitySchema getSchema() {
        return schema;
    }

    @Override
    public Object getValueOfField(Field field) {
        checkFieldExists(getSchema(), field);
        return values.get(field);
    }

    @Override
    public void setValueOfField(Field field, Object value) {
        checkFieldExists(getSchema(), field);
        values.put(field, value);
    }

    @Override
    public String toString() {
        MoreObjects.ToStringHelper helper = MoreObjects.toStringHelper("SimpleEntity")
                .omitNullValues()
                .add("schema", schema);
        for (Field field : schema.getFields()) {
            helper.add(field.toString(), values.get(field));
        }
        return helper.toString();
    }

    // TODO hashCode and equals

}