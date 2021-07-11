package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import java.util.List;

public interface EntityWithEnrichments {

    EntityFieldReadAccessor getEntity();

    List<Enrichment> getEnrichments();

}
