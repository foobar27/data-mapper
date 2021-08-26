package com.github.foobar27.datamapper.pipeline;

import static com.google.common.base.Preconditions.checkNotNull;

public class TypedValueOrEnrichment<T> {

    private final ValueOrEnrichment delegate;

    public TypedValueOrEnrichment(ValueOrEnrichment delegate) {
        this.delegate = checkNotNull(delegate);
    }

    public void set(T value) {
        this.delegate.set(value);
    }

    public void use(Enrichment enrichment) {
        this.delegate.use(enrichment);
    }

}