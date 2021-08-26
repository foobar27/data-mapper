package com.github.foobar27.datamapper.schema;

import java.util.Set;

public interface EntityFieldReadAccessor {

    EntitySchema getSchema();

    Object getValueOfField(Field field);

    default EntityFieldReadAccessor filterFields(Set<Field> fields) {
        return FilteredEntityFieldReadAccessor.filterFields(this, fields);
    }

    default void mergeInto(EntityFieldReadWriteAccessor output) {
        for (Field field : this.getSchema().getFields()) {
            output.setValueOfField(field, getValueOfField(field));
        }
    }
}
