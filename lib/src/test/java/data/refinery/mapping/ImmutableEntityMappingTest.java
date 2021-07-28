package data.refinery.mapping;

import com.google.common.collect.ImmutableList;
import data.refinery.schema.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ImmutableEntityMappingTest {

    private final NamedEntitySchema bigInputSchema = NamedEntitySchema.createFromLocalNames("bigInput", ImmutableList.of("f1", "f2", "f3"));
    private final NamedEntitySchema bigOutputSchema = NamedEntitySchema.createFromLocalNames("bigOutput", ImmutableList.of("f1", "f2", "f3"));

    private final NamedEntitySchema smallInputSchema = NamedEntitySchema.createFromLocalNames("smallInput", ImmutableList.of("f1", "f2"));
    private final NamedEntitySchema smallOutputSchema = NamedEntitySchema.createFromLocalNames("smallOutput", ImmutableList.of("f1", "f2"));

    private final ImmutableEntityMapping completeMapping =
            ImmutableEntityMapping.newBuilder(bigInputSchema, bigOutputSchema)
                    .mapField(bigInputSchema.getFieldByLocalName("f1"), bigOutputSchema.getFieldByLocalName("f1"))
                    .mapField(bigInputSchema.getFieldByLocalName("f2"), bigOutputSchema.getFieldByLocalName("f2"))
                    .mapField(bigInputSchema.getFieldByLocalName("f3"), bigOutputSchema.getFieldByLocalName("f3"))
                    .build();

    private final ImmutableEntityMapping incompleteMapping =
            ImmutableEntityMapping.newBuilder(bigInputSchema, bigOutputSchema)
                    .mapField(bigInputSchema.getFieldByLocalName("f1"), bigOutputSchema.getFieldByLocalName("f1"))
                    .mapField(bigInputSchema.getFieldByLocalName("f2"), bigOutputSchema.getFieldByLocalName("f2"))
                    .build();

    private final ImmutableEntityMapping biggerInputMapping =
            ImmutableEntityMapping.newBuilder(bigInputSchema, smallOutputSchema)
                    .mapField(bigInputSchema.getFieldByLocalName("f1"), smallOutputSchema.getFieldByLocalName("f1"))
                    .mapField(bigInputSchema.getFieldByLocalName("f2"), smallOutputSchema.getFieldByLocalName("f2"))
                    .build();

    private final ImmutableEntityMapping biggerOutputMapping =
            ImmutableEntityMapping.newBuilder(smallInputSchema, bigOutputSchema)
                    .mapField(smallInputSchema.getFieldByLocalName("f1"), bigOutputSchema.getFieldByLocalName("f1"))
                    .mapField(smallInputSchema.getFieldByLocalName("f2"), bigOutputSchema.getFieldByLocalName("f2"))
                    .build();

    @Test
    public void verification() {
        assertTrue(completeMapping.toBuilder().isInputSchemaComplete());
        assertTrue(completeMapping.toBuilder().isOutputSchemaComplete());
        assertFalse(incompleteMapping.toBuilder().isInputSchemaComplete());
        assertFalse(incompleteMapping.toBuilder().isOutputSchemaComplete());
        assertFalse(biggerInputMapping.toBuilder().isInputSchemaComplete());
        assertTrue(biggerInputMapping.toBuilder().isOutputSchemaComplete());
        assertTrue(biggerOutputMapping.toBuilder().isInputSchemaComplete());
        assertFalse(biggerOutputMapping.toBuilder().isOutputSchemaComplete());
    }

    private int normalizeInputAndVerify(ImmutableEntityMapping mapping) {
        ImmutableEntityMapping.Builder builder = mapping.toBuilder().normalizeInputSchema();
        assertTrue(builder.isInputSchemaComplete());
        return builder.build().getInputSchema().getFields().size();
    }

    private int normalizeOutputAndVerify(ImmutableEntityMapping mapping) {
        ImmutableEntityMapping.Builder builder = mapping.toBuilder().normalizeOutputSchema();
        assertTrue(builder.isOutputSchemaComplete());
        return builder.build().getOutputSchema().getFields().size();
    }

    @Test
    public void normalizeMapping() {
        assertEquals(3, normalizeInputAndVerify(completeMapping));
        assertEquals(3, normalizeOutputAndVerify(completeMapping));
        assertEquals(2, normalizeInputAndVerify(biggerInputMapping));
        assertEquals(2, normalizeOutputAndVerify(biggerOutputMapping));
    }

    // TODO test reverse mapping
    // TODO test argument checking, duplicate checking

}
