package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;
import com.github.foobar27.datamapper.schema.Field;
import com.github.foobar27.datamapper.utils.Java8Compat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public abstract class AbstractEntityWithEnrichments implements EntityWithEnrichments {

    private final EntitySchema schema;
    private final Map<Field, ValueOrEnrichment> fields = new HashMap<>();

    protected AbstractEntityWithEnrichments(EntitySchema schema) {
        this.schema = checkNotNull(schema);
    }

    protected <T> TypedValueOrEnrichment<T> register(Field field) {
        if (fields.containsKey(field)) {
            throw new IllegalArgumentException("Duplicate field: " + field);
        }
        checkArgument(schema.getFields().contains(field), "schema %s must contain field %s", schema, field);
        ValueOrEnrichment result = new ValueOrEnrichment(field);
        fields.put(field, result);
        return new TypedValueOrEnrichment<>(result);
    }

    @Override
    public List<Enrichment> getEnrichments() {
        return fields.values()
                .stream()
                .flatMap(x -> Java8Compat.optionalToStream(x.asEnrichment()))
                .collect(Collectors.toList());
    }

    public Object getValueOfField(Field field) {
        return fields.get(field)
        .getValueOrNull();
    }

    @Override
    public void mergeInto(EntityFieldReadWriteAccessor output) {
        for (Field field : this.getSchema().getFields()) {
            if (fields.get(field).isValue()) {
                output.setValueOfField(field, getValueOfField(field));
            }
        }
    }

    @Override
    public EntitySchema getSchema() {
        return schema;
    }
}
