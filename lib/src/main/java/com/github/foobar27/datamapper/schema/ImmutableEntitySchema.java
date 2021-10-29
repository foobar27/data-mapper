package com.github.foobar27.datamapper.schema;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

public class ImmutableEntitySchema implements EntitySchema {

    private final List<Field> fields;

    private ImmutableEntitySchema(Builder builder) {
        this.fields = ImmutableList.copyOf(builder.fields);
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("ImmutableEntitySchema")
                .add("fields", fields)
                .toString();
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder {

        private List<Field> fields;

        private Builder() {
            this.fields = new ArrayList<>();
        }

        private Builder(ImmutableEntitySchema schema) {
            this.fields = new ArrayList<>(schema.fields);
        }

        public Builder addField(Field field) {
            checkArgument(!fields.contains(field), "Duplicate field %s", field);
            fields.add(field);
            return this;
        }

        public Builder addAllFields(Iterable<? extends Field> fields) {
            fields.forEach(this::addField);
            return this;
        }

        public ImmutableEntitySchema build() {
            return new ImmutableEntitySchema(this);
        }
    }

}
