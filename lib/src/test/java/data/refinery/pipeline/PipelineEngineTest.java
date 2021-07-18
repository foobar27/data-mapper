package data.refinery.pipeline;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.schema.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static data.refinery.schema.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PipelineEngineTest {

    @Test
    public void calculateFullName() throws ExecutionException, InterruptedException {
        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(ConcatStringsCalculation.getInstance(),
                        new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                                .enableAutoApply())
                .build();

        ConcatStringsCalculation.ParameterSchema parameterSchema = ConcatStringsCalculation.parameterSchema();

        SimpleEntity person = new SimpleEntity(personSchema());
        person.setValueOfField(personSchema().firstName(), "John");
        person.setValueOfField(personSchema().lastName(), "Doe");
        SimpleEntity parameters = new SimpleEntity(parameterSchema);
        parameters.setValueOfField(parameterSchema.middle(), " ");
        Enrichment fullNameEnrichment = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        ConcatStringsCalculation.getInstance(),
                        ProfunctorCalculationTest.fullNameCalculation),
                parameters);

        PipelineEngine engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        new PipelineDefinition(personSchema(), ImmutableList.of(fullNameEnrichment)),
                        calculationFactory,
                        () -> new SimpleEntity(personSchema()),
                        MoreExecutors.directExecutor());


        EntityFieldReadAccessor output = engine.process(person).get();
        assertThat(output.getValueOfField(personSchema().firstName()), is("John"));
        assertThat(output.getValueOfField(personSchema().lastName()), is("Doe"));
        assertThat(output.getValueOfField(personSchema().fullName()), is("John Doe"));
    }

    @Test
    public void calculateChains() throws ExecutionException, InterruptedException {

        Field a0 = new NamedField("a0");
        Field a1 = new NamedField("a1");
        Field a2 = new NamedField("a2");
        Field b0 = new NamedField("b0");
        Field b1 = new NamedField("b1");
        Field b2 = new NamedField("b2");
        EntitySchema schema = new NamedEntitySchema("ChainedEntity", ImmutableList.of(a0, a1, a2, b0, b1, b2));

        AppendConstantCalculation.InputSchema inputSchema = AppendConstantCalculation.inputSchema();
        AppendConstantCalculation.ParameterSchema parameterSchema = AppendConstantCalculation.parameterSchema();
        AppendConstantCalculation.OutputSchema outputSchema = AppendConstantCalculation.outputSchema();

        SimpleEntity parametersX = new SimpleEntity(parameterSchema);
        parametersX.setValueOfField(parameterSchema.constant(), "X");

        SimpleEntity parametersY = new SimpleEntity(parameterSchema);
        parametersY.setValueOfField(parameterSchema.constant(), "Y");

        Enrichment enrichmentA1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(a0, inputSchema.value())
                                .rightMapField(outputSchema.value(), a1)
                                .verifyNormalizeAndBuild()),
                parametersX);
        Enrichment enrichmentA2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(a1, inputSchema.value())
                                .rightMapField(outputSchema.value(), a2)
                                .verifyNormalizeAndBuild()),
                parametersY);
        Enrichment enrichmentB1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(b0, inputSchema.value())
                                .rightMapField(outputSchema.value(), b1)
                                .verifyNormalizeAndBuild()),
                parametersX);
        Enrichment enrichmentB2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(b1, inputSchema.value())
                                .rightMapField(outputSchema.value(), b2)
                                .verifyNormalizeAndBuild()),
                parametersY);

        SimpleEntity entity = new SimpleEntity(schema);
        entity.setValueOfField(a0, "A");
        entity.setValueOfField(b0, "B");

        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(AppendConstantCalculation.getInstance(),
                        new AppendConstantCalculationImplementation(MoreExecutors.directExecutor())
                                .enableAutoApply())
                .build();
        PipelineEngine engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        new PipelineDefinition(schema, ImmutableList.of(enrichmentA1, enrichmentA2, enrichmentB1, enrichmentB2)),
                        calculationFactory,
                        () -> new SimpleEntity(schema),
                        MoreExecutors.directExecutor());
        EntityFieldReadAccessor output = engine.process(entity).get();
        assertThat(output.getValueOfField(a0), is("A"));
        assertThat(output.getValueOfField(a1), is("AX"));
        assertThat(output.getValueOfField(a2), is("AXY"));
        assertThat(output.getValueOfField(b0), is("B"));
        assertThat(output.getValueOfField(b1), is("BX"));
        assertThat(output.getValueOfField(b2), is("BXY"));
    }

    @Test
    public void calculateDeepChain() throws ExecutionException, InterruptedException {
        // Similar like calculateChains, but we now use a deep chain to see if we get a stack overflow (and we only have 1 chain).
        int numberOfFields = 200;
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < numberOfFields; ++i) {
            fields.add(new NamedField("a" + i));
        }
        EntitySchema schema = new NamedEntitySchema("ChainedEntity", fields);

        AppendConstantCalculation.InputSchema inputSchema = AppendConstantCalculation.inputSchema();
        AppendConstantCalculation.ParameterSchema parameterSchema = AppendConstantCalculation.parameterSchema();
        AppendConstantCalculation.OutputSchema outputSchema = AppendConstantCalculation.outputSchema();

        SimpleEntity parameters = new SimpleEntity(parameterSchema);
        parameters.setValueOfField(parameterSchema.constant(), ""); // do not append anything real

        List<Enrichment> allEnrichments = new ArrayList<>();
        for (int i = 1; i < numberOfFields; ++i) {
            Enrichment enrichment = new ImmutableEnrichment(
                    new ImmutableMappedCalculation(
                            AppendConstantCalculation.getInstance(),
                            ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema)
                                    .leftMapField(fields.get(i - 1), inputSchema.value())
                                    .rightMapField(outputSchema.value(), fields.get(i))
                                    .verifyNormalizeAndBuild()),
                    parameters);
            allEnrichments.add(enrichment);
        }
        PipelineDefinition enrichments = new PipelineDefinition(schema, allEnrichments);

        SimpleEntity entity = new SimpleEntity(schema);
        entity.setValueOfField(fields.get(0), "A");

        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(AppendConstantCalculation.getInstance(),
                        new AppendConstantCalculationImplementation(MoreExecutors.directExecutor())
                                .enableAutoApply())
                .build();
        Stopwatch sw = Stopwatch.createStarted();
        PipelineEngine engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        enrichments,
                        calculationFactory,
                        () -> new SimpleEntity(schema),
                        MoreExecutors.directExecutor());
        for (int i = 0; i < 1000; ++i) {
            EntityFieldReadAccessor output = engine.process(entity).get();
            assertThat(output.getValueOfField(fields.get(0)), is("A"));
            assertThat(output.getValueOfField(fields.get(1)), is("A"));
            assertThat(output.getValueOfField(fields.get(numberOfFields - 1)), is("A"));
        }
        System.out.printf("Took: %sms%n", sw.elapsed(TimeUnit.MILLISECONDS));
    }

    // TODO test cancellation

}
