package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import data.refinery.schema.Field;

import java.util.List;
import java.util.Set;

public final class EnrichmentList {

    private final List<Enrichment> enrichments;
    private final Set<Field> allInputFields;
    private final Set<Field> allOutputFields;

    public EnrichmentList(List<Enrichment> enrichments) {
        this.enrichments = ImmutableList.copyOf(enrichments);
        this.allInputFields = getEnrichments().stream()
                .flatMap(enrichment ->
                        enrichment.getMappedCalculation()
                                .getMapping()
                                .getLeftMapping()
                                .getInputSchema()
                                .getFields()
                                .stream())
                .collect(ImmutableSet.toImmutableSet());
        this.allOutputFields = getEnrichments().stream()
                .flatMap(enrichment ->
                        enrichment.getMappedCalculation()
                                .getMapping()
                                .getRightMapping()
                                .getOutputSchema()
                                .getFields()
                                .stream())
                .collect(ImmutableSet.toImmutableSet());
    }

    public List<Enrichment> getEnrichments() {
        return enrichments;
    }

    public Set<Field> getAllInputFields() {
        return allInputFields;
    }

    public Set<Field> getAllOutputFields() {
        return allOutputFields;
    }


}
