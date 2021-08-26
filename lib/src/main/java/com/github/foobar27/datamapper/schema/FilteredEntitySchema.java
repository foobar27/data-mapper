package com.github.foobar27.datamapper.schema;

import com.google.common.collect.Sets;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

final class FilteredEntitySchema implements EntitySchema {

    private final EntitySchema delegate;
    private final List<Field> fields;

    public static EntitySchema filterKeys(EntitySchema delegate, Set<Field> fields) {
        checkNotNull(delegate);
        checkNotNull(fields);
        if (fields.isEmpty()) {
            return EmptyEntitySchema.getInstance();
        }
        if (!delegate.getFields().containsAll(fields)) {
            throw new UnsupportedOperationException(String.format(
                    "Cannot filter non-existing fields, tried to filter fields %s in %s",
                    fields,
                    delegate));
        }
        if (fields.containsAll(delegate.getFields())) {
            return delegate;
        }
        // TODO fast path: double filtering
        // TODO fast path if result would be same as delegate
        return new FilteredEntitySchema(
                delegate,
                delegate.getFields()
                        .stream()
                        .filter(fields::contains)
                        .collect(Collectors.toList()));
    }

    public static EntitySchema removeKeys(EntitySchema delegate, Set<Field> fields) {
        return filterKeys(delegate, Sets.difference(new HashSet<>(delegate.getFields()), fields));
    }

    private FilteredEntitySchema(EntitySchema delegate, List<Field> fields) {
        this.delegate = checkNotNull(delegate);
        this.fields = checkNotNull(fields);
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

}
