package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.EntityFieldReadAccessor;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ImmutableEntityWithEnrichments implements EntityWithEnrichments {

    private final EntityFieldReadAccessor entity;
    private final List<Enrichment> enrichments;

    public ImmutableEntityWithEnrichments(EntityFieldReadAccessor entity, List<Enrichment> enrichments) {
        this.entity = checkNotNull(entity);
        this.enrichments = ImmutableList.copyOf(enrichments);
    }

    @Override
    public EntityFieldReadAccessor getEntity() {
        return entity;
    }

    @Override
    public List<Enrichment> getEnrichments() {
        return enrichments;
    }
}
