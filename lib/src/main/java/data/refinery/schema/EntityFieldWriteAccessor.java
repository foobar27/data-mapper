package data.refinery.schema;

public interface EntityFieldWriteAccessor {

    EntitySchema getSchema();

    void setValueOfField(Field field, Object value);

}
