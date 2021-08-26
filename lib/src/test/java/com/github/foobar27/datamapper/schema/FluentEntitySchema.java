package com.github.foobar27.datamapper.schema;

import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public abstract class FluentEntitySchema implements EntitySchema {

    private final String entityName;
    private final Set<String> seenSuffixes = new HashSet<>();
    private final List<Field> fields = new ArrayList<>();

    protected FluentEntitySchema(String entityName) {
        this.entityName = checkNotNull(entityName);
    }

    protected Field register(String suffix) {
        if (!seenSuffixes.add(suffix)) {
            throw new IllegalArgumentException("Duplicate suffix: " + suffix);
        }
        NamedField field = new NamedField(entityName + "." + suffix);
        fields.add(field);
        return field;
    }

    @Override
    public String toString() {
        return entityName;
    }

    @Override
    public List<Field> getFields() {
        return ImmutableList.copyOf(fields);
    }
}