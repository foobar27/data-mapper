package data.refinery.pipeline;

import data.refinery.schema.EntityFieldReadAccessor;

import java.util.Collection;
import java.util.List;

public interface EntityWithEnrichments extends EntityFieldReadAccessor {

    List<Enrichment> getEnrichments();

}
