package com.github.foobar27.datamapper.mapping;

import com.github.foobar27.datamapper.schema.EntitySchema;
import com.github.foobar27.datamapper.schema.Field;

import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

final class ReversedEntityMapping implements EntityMapping {

    private final EntityMapping delegate;

    public static EntityMapping reverse(EntityMapping delegate) {
        checkNotNull(delegate);
        if (delegate instanceof ReversedEntityMapping) {
            return ((ReversedEntityMapping) delegate).delegate;
        }
        return new ReversedEntityMapping(delegate);
    }

    private ReversedEntityMapping(EntityMapping delegate) {
        this.delegate = delegate;
    }

    @Override
    public EntitySchema getInputSchema() {
        return delegate.getOutputSchema();
    }

    @Override
    public EntitySchema getOutputSchema() {
        return delegate.getInputSchema();
    }

    @Override
    public Map<Field, Field> getMapping() {
        return delegate.getReverseMapping();
    }

    @Override
    public Map<Field, Field> getReverseMapping() {
        return delegate.getMapping();
    }
}
