package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public final class EnrichmentList {

    private final EntitySchema inputSchema;
    private final List<Enrichment> enrichments;
    private final Set<Field> allInputFields;
    private final Set<Field> allOutputFields;
    private final EntitySchema fixedSchema;

    public EnrichmentList(EntitySchema inputSchema, List<Enrichment> enrichments) {
        this.inputSchema = checkNotNull(inputSchema);
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
        this.fixedSchema = inputSchema.removeKeys(this.allOutputFields);
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

    public EntitySchema getFixedSchema() {
        return fixedSchema;
    }

}
