package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.graph.ElementOrder;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.Graphs;
import com.google.common.graph.ImmutableGraph;
import data.refinery.schema.EntitySchema;
import data.refinery.schema.Field;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

public final class EnrichmentList {

    private final EntitySchema inputSchema;
    private final List<Enrichment> enrichments;
    private final Set<Field> allInputFields;
    private final Set<Field> allOutputFields;
    private final EntitySchema fixedSchema;
    private final ImmutableGraph<Enrichment> enrichmentGraph;

    public EnrichmentList(EntitySchema inputSchema, List<Enrichment> enrichments) {
        this.inputSchema = checkNotNull(inputSchema);
        this.enrichments = ImmutableList.copyOf(enrichments);
        this.allInputFields = getEnrichments().stream()
                .flatMap(enrichment -> enrichment.getInputFields().stream())
                .collect(ImmutableSet.toImmutableSet());
        this.allOutputFields = getEnrichments().stream()
                .flatMap(enrichment -> enrichment.getOutputFields().stream())
                .collect(ImmutableSet.toImmutableSet());
        this.fixedSchema = inputSchema.removeKeys(this.allOutputFields);
        this.enrichmentGraph = buildEnrichmentGraph(enrichments);
    }

    private static <T> boolean intersect(List<T> left, List<T> right) {
        return left.stream().anyMatch(right::contains);
    }

    private static ImmutableGraph<Enrichment> buildEnrichmentGraph(List<Enrichment> enrichments) {
        ImmutableGraph.Builder<Enrichment> builder = GraphBuilder
                .directed()
                .nodeOrder(ElementOrder.insertion())
                .allowsSelfLoops(false)
                .expectedNodeCount(enrichments.size())
                .immutable();
        enrichments.forEach(builder::addNode);
        // Add edges, and verify at most one incoming edge for each field
        Set<Field> seenOutputFields = new HashSet<>();
        for (Enrichment left : enrichments) {
            for (Field field : left.getOutputFields()) {
                if (!seenOutputFields.add(field)) {
                    throw new IllegalArgumentException(String.format("Field %s is set by more than one enrichment!", field));
                }
            }
            for (Enrichment right : enrichments) {
                if (left == right) {
                    if (intersect(left.getInputFields(), left.getOutputFields())) {
                        throw new IllegalArgumentException(String.format("Loop at enrichment %s (input fields: %s, output fields: %s)",
                                left,
                                left.getInputFields(),
                                left.getInputFields()));
                    }
                    continue;
                }
                if (intersect(left.getOutputFields(), right.getInputFields())) {
                    builder.putEdge(left, right);
                }
            }
        }
        ImmutableGraph<Enrichment> graph = builder.build();
        if (Graphs.hasCycle(graph)) {
            throw new IllegalArgumentException("Cycle detected!");
        }
        return graph;
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

    public ImmutableGraph<Enrichment> getEnrichmentGraph() {
        return enrichmentGraph;
    }

}
