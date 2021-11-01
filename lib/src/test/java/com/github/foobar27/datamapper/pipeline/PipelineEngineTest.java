package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.boilerplate.*;
import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.util.concurrent.MoreExecutors;
import com.github.foobar27.datamapper.conversion.SimpleEntityFactory;
import com.github.foobar27.datamapper.mapping.ImmutableEntityAdapter;
import com.github.foobar27.datamapper.schema.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static com.github.foobar27.datamapper.boilerplate.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PipelineEngineTest {

    private final CalculationFactory concatCalculationFactory = DefaultCalculationFactory.newBuilder()
            .register(ConcatStringsCalculationDefinition.getInstance(),
                    new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                            .enableAutoApply())
            .build();

    private final SimpleEntity parameters = new SimpleEntity(ConcatStringsCalculationDefinition.parameterSchema());
    private final Enrichment fullNameEnrichment = new ImmutableEnrichment(
            MappedCalculationTest.mappedCalculation,
            parameters);

    public PipelineEngineTest() {
        parameters.setValueOfField(ConcatStringsCalculationDefinition.parameterSchema().middle(), " ");
    }

    @Test
    public void calculateFullName() throws ExecutionException, InterruptedException {
        PojoPerson person = new PojoPerson();
        person.setFirstName("John");
        person.setLastName("Doe");

        PipelineEngine<ImmutablePerson, ImmutablePerson.Builder> engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        new PipelineDefinition(personSchema(), ImmutableList.of(fullNameEnrichment)),
                        concatCalculationFactory,
                        PersonFactory.getInstance(),
                        MoreExecutors.directExecutor());

        ImmutablePerson output = engine.process(person).get();
        assertThat(output.getFirstName(), is("John"));
        assertThat(output.getLastName(), is("Doe"));
        assertThat(output.getFullName(), is("John Doe"));
    }

    @Test
    public void valueOrEnrichmentTest() throws ExecutionException, InterruptedException {
        PersonWithEnrichments person = new PersonWithEnrichments();
        person.firstName().set("John");
        person.lastName().set("Doe");
        person.fullName().use(fullNameEnrichment);
        person.age().set(0); // TODO it should also work without this!

        PipelineEngine<ImmutablePerson, ImmutablePerson.Builder> engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        new PipelineDefinition(personSchema(), person.getEnrichments()),
                        concatCalculationFactory,
                        PersonFactory.getInstance(),
                        MoreExecutors.directExecutor());

        ImmutablePerson output = engine.process(person).get();
        assertThat(output.getFirstName(), is("John"));
        assertThat(output.getLastName(), is("Doe"));
        assertThat(output.getFullName(), is("John Doe"));
    }

    @Test
    public void calculateChains() throws ExecutionException, InterruptedException {

        NamedField a0 = new NamedField("a0");
        NamedField a1 = new NamedField("a1");
        NamedField a2 = new NamedField("a2");
        NamedField b0 = new NamedField("b0");
        NamedField b1 = new NamedField("b1");
        NamedField b2 = new NamedField("b2");
        EntitySchema schema = new NamedEntitySchema("ChainedEntity", ImmutableList.of(a0, a1, a2, b0, b1, b2));

        AppendConstantCalculationDefinition.InputSchema inputSchema = AppendConstantCalculationDefinition.inputSchema();
        AppendConstantCalculationDefinition.ParametersSchema parametersSchema = AppendConstantCalculationDefinition.parameterSchema();
        AppendConstantCalculationDefinition.OutputSchema outputSchema = AppendConstantCalculationDefinition.outputSchema();

        SimpleEntity parametersX = new SimpleEntity(parametersSchema);
        parametersX.setValueOfField(parametersSchema.constant(), "X");

        SimpleEntity parametersY = new SimpleEntity(parametersSchema);
        parametersY.setValueOfField(parametersSchema.constant(), "Y");

        Enrichment enrichmentA1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(AppendConstantCalculationDefinition.getInstance(),
                        ImmutableEntityAdapter.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(a0, inputSchema.value())
                                .rightMapField(outputSchema.value(), a1)
                                .verifyNormalizeAndBuild()),
                parametersX);
        Enrichment enrichmentA2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculationDefinition.getInstance(),
                        ImmutableEntityAdapter.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(a1, inputSchema.value())
                                .rightMapField(outputSchema.value(), a2)
                                .verifyNormalizeAndBuild()),
                parametersY);
        Enrichment enrichmentB1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculationDefinition.getInstance(),
                        ImmutableEntityAdapter.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(b0, inputSchema.value())
                                .rightMapField(outputSchema.value(), b1)
                                .verifyNormalizeAndBuild()),
                parametersX);
        Enrichment enrichmentB2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculationDefinition.getInstance(),
                        ImmutableEntityAdapter.newBuilder(schema, inputSchema, outputSchema, schema)
                                .leftMapField(b1, inputSchema.value())
                                .rightMapField(outputSchema.value(), b2)
                                .verifyNormalizeAndBuild()),
                parametersY);

        SimpleEntity entity = new SimpleEntity(schema);
        entity.setValueOfField(a0, "A");
        entity.setValueOfField(b0, "B");

        CalculationFactory calculationFactory = DefaultCalculationFactory.newBuilder()
                .register(AppendConstantCalculationDefinition.getInstance(),
                        new AppendConstantCalculationImplementation(MoreExecutors.directExecutor())
                                .enableAutoApply())
                .build();
        PipelineEngine<SimpleEntity, SimpleEntity> engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        new PipelineDefinition(schema, ImmutableList.of(enrichmentA1, enrichmentA2, enrichmentB1, enrichmentB2)),
                        calculationFactory,
                        new SimpleEntityFactory(schema),
                        MoreExecutors.directExecutor());
        EntityFieldReadAccessor output = engine.process(entity).get();
        assertThat(output.getValueOfField(a0), is("A"));
        assertThat(output.getValueOfField(a1), is("AX"));
        assertThat(output.getValueOfField(a2), is("AXY"));
        assertThat(output.getValueOfField(b0), is("B"));
        assertThat(output.getValueOfField(b1), is("BX"));
        assertThat(output.getValueOfField(b2), is("BXY"));
    }

    private PipelineDefinition buildDeepChain() {
        // Similar like calculateChains, but we now use a deep chain to see if we get a stack overflow (and we only have 1 chain).
        int numberOfFields = 200;
        List<NamedField> fields = new ArrayList<>();
        for (int i = 0; i < numberOfFields; ++i) {
            fields.add(new NamedField("a" + i));
        }
        EntitySchema schema = new NamedEntitySchema("ChainedEntity", fields);

        AppendConstantCalculationDefinition.InputSchema inputSchema = AppendConstantCalculationDefinition.inputSchema();
        AppendConstantCalculationDefinition.ParametersSchema parametersSchema = AppendConstantCalculationDefinition.parameterSchema();
        AppendConstantCalculationDefinition.OutputSchema outputSchema = AppendConstantCalculationDefinition.outputSchema();

        SimpleEntity parameters = new SimpleEntity(parametersSchema);
        parameters.setValueOfField(parametersSchema.constant(), ""); // do not append anything real

        List<Enrichment> allEnrichments = new ArrayList<>();
        for (int i = 1; i < numberOfFields; ++i) {
            Enrichment enrichment = new ImmutableEnrichment(
                    new ImmutableMappedCalculation(
                            AppendConstantCalculationDefinition.getInstance(),
                            ImmutableEntityAdapter.newBuilder(schema, inputSchema, outputSchema, schema)
                                    .leftMapField(fields.get(i - 1), inputSchema.value())
                                    .rightMapField(outputSchema.value(), fields.get(i))
                                    .verifyNormalizeAndBuild()),
                    parameters);
            allEnrichments.add(enrichment);
        }
        Collections.shuffle(allEnrichments);
        return new PipelineDefinition(schema, allEnrichments);
    }

    @Test
    public void calculateDeepChain() throws ExecutionException, InterruptedException {
        PipelineDefinition pipelineDefinition = buildDeepChain();

        List<Field> fields = pipelineDefinition.getInputSchema().getFields();
        SimpleEntity entity = new SimpleEntity(pipelineDefinition.getFixedSchema());
        entity.setValueOfField(fields.get(0), "A");

        CalculationFactory calculationFactory = DefaultCalculationFactory.newBuilder()
                .register(AppendConstantCalculationDefinition.getInstance(),
                        new AppendConstantCalculationImplementation(MoreExecutors.directExecutor())
                                .enableAutoApply())
                .build();
        Stopwatch sw = Stopwatch.createStarted();
        PipelineEngine<SimpleEntity, SimpleEntity> engine = new PipelineEngineFactory()
                .createPipelineEngine(
                        pipelineDefinition,
                        calculationFactory,
                        new SimpleEntityFactory(pipelineDefinition.getInputSchema()),
                        MoreExecutors.directExecutor());
        for (int i = 0; i < 1000; ++i) {
            EntityFieldReadAccessor output = engine.process(entity).get();
            assertThat(output.getValueOfField(fields.get(0)), is("A"));
            assertThat(output.getValueOfField(fields.get(1)), is("A"));
            assertThat(output.getValueOfField(fields.get(fields.size() - 1)), is("A"));
        }
        System.out.printf("Took: %sms%n", sw.elapsed(TimeUnit.MILLISECONDS));
    }

    // TODO test cancellation

}
