package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadWriteAccessor;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

import java.util.Collection;
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

    protected ValueOrEnrichment register(Field field) {
        if (fields.containsKey(field)) {
            throw new IllegalArgumentException("Duplicate field: " + field);
        }
        checkArgument(schema.getFields().contains(field), "schema %s must contain field %s", schema, field);
        ValueOrEnrichment result = new ValueOrEnrichment(field);
        fields.put(field, result);
        return result;
    }

    @Override
    public List<Enrichment> getEnrichments() {
        return fields.values()
                .stream()
                .flatMap(x -> x.asEnrichment().stream())
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
