package data.refinery.schema;

import static com.google.common.base.Preconditions.checkNotNull;

public class NoSuchFieldException extends RuntimeException{

    private final EntitySchema schema;
    private final Field field;

    public static Field checkFieldExists(EntitySchema schema, Field field) {
        if (schema.getFields().contains(field)) {
            return field;
        }
        throw new NoSuchFieldException(schema, field);
    }

    public NoSuchFieldException(EntitySchema schema, Field field) {
        super(String.format("No field %s in schema %s", field, schema));
        this.schema = checkNotNull(schema);
        this.field = checkNotNull(field);
    }

    public Field getField() {
        return field;
    }

}
