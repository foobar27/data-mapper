package com.github.foobar27.datamapper.schema;

public interface EntityFieldWriteAccessor {

    EntitySchema getSchema();

    void setValueOfField(Field field, Object value);

}
