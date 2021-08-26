package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;

import java.util.List;

public interface EntityWithEnrichments extends EntityFieldReadAccessor {

    List<Enrichment> getEnrichments();

}
