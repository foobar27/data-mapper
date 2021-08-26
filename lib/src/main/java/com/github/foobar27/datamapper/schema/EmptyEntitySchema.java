package com.github.foobar27.datamapper.schema;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

final class EmptyEntitySchema implements EntitySchema {

    private static final EmptyEntitySchema instance = new EmptyEntitySchema();

    public static EmptyEntitySchema getInstance() {
        return instance;
    }

    private EmptyEntitySchema() {
        // Disable public constructor
    }

    @Override
    public List<Field> getFields() {
        return Collections.emptyList();
    }

    @Override
    public EntitySchema filterKeys(Set<Field> fields) {
        checkArgument(fields.isEmpty());
        return this;
    }
}
