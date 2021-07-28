package data.refinery.pipeline;

import com.google.common.base.MoreObjects;
import data.refinery.schema.Field;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public final class ValueOrEnrichment {

    private final Field field;
    private Object value;
    private Enrichment enrichment;

    public ValueOrEnrichment(Field field) {
        this.field = field;
    }

    public void set(Object value) {
        checkArgument(enrichment == null);
        this.value = value;
    }

    public void use(Enrichment enrichment) {
        checkArgument(this.enrichment == null); // TODO don't know how to compose enrichments yet
        checkArgument(enrichment.getOutputFields().size() == 1);
        checkArgument(enrichment.getOutputFields().get(0).equals(field));
        this.enrichment = enrichment;
    }

    public boolean isValue() {
        return enrichment == null;
    }

    public Object getValueOrNull() {
        return value;
    }

    public boolean isEnrichment() {
        return !isValue();
    }

    public Optional<Enrichment> asEnrichment() {
        if (isValue()) {
            return Optional.empty();
        }
        return Optional.of(enrichment);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("ValueOrEnrichment")
                .omitNullValues()
                .add("value", value)
                .add("enrichment", enrichment)
                .toString();
    }

}
