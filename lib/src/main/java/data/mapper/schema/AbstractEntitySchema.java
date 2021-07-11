package data.mapper.schema;

import java.util.ArrayList;
import java.util.List;

// TODO Remove (migrated)
public abstract class AbstractEntitySchema implements EntitySchema {

    private final List<Field<?>> fields = new ArrayList<>();

    protected <NativeType, FieldType extends Field<NativeType>> FieldType registerField(FieldType field) {
        fields.add(field);
        return field;
    }

    @Override
    public String toString() {
        return "Entity[" + name() + "]";
    }

}
