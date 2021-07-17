package data.refinery.pipeline;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.schema.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static data.refinery.pipeline.AppendConstantCalculation.*;
import static data.refinery.pipeline.ConcatStringsCalculation.parametersMiddle;
import static data.refinery.schema.ExampleSchemata.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class PipelineEngineTest {

    @Test
    public void calculateFullName() throws ExecutionException, InterruptedException {
        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(ConcatStringsCalculation.getInstance(),
                        new ConcatStringsCalculationImplementation()
                                .enableAutoApply())
                .build();
        PipelineEngine engine = new PipelineEngine(calculationFactory, () -> new SimpleEntity(ExampleSchemata.person),
                MoreExecutors.directExecutor());

        SimpleEntity person = new SimpleEntity(ExampleSchemata.person);
        person.setValueOfField(personFirstName, "John");
        person.setValueOfField(personLastName, "Doe");
        SimpleEntity parameters = new SimpleEntity(ConcatStringsCalculation.parameterSchema);
        parameters.setValueOfField(parametersMiddle, " ");
        Enrichment fullNameEnrichment = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        ConcatStringsCalculation.getInstance(),
                        ProfunctorCalculationTest.fullNameCalculation),
                parameters);
        ImmutableEntityWithEnrichments entityWitEnrichments = new ImmutableEntityWithEnrichments(person, ImmutableList.of(fullNameEnrichment));

        EntityFieldReadWriteAccessor output = engine.process(entityWitEnrichments).get();
        assertThat(output.getValueOfField(personFirstName), is("John"));
        assertThat(output.getValueOfField(personLastName), is("Doe"));
        assertThat(output.getValueOfField(personFullName), is("John Doe"));
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

        SimpleEntity parametersX = new SimpleEntity(AppendConstantCalculation.parameterSchema);
        parametersX.setValueOfField(parameterConstant, "X");

        SimpleEntity parametersY = new SimpleEntity(AppendConstantCalculation.parameterSchema);
        parametersY.setValueOfField(parameterConstant, "Y");

        Enrichment enrichmentA1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(a1))) // TODO why can't this filter be implicit?
                                .leftMapField(a0, inputValue)
                                .rightMapField(outputValue, a1)
                                .build()),
                parametersX);
        Enrichment enrichmentA2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(a2)))
                                .leftMapField(a1, inputValue)
                                .rightMapField(outputValue, a2)
                                .build()),
                parametersY);
        Enrichment enrichmentB1 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(b1)))
                                .leftMapField(b0, inputValue)
                                .rightMapField(outputValue, b1)
                                .build()),
                parametersX);
        Enrichment enrichmentB2 = new ImmutableEnrichment(
                new ImmutableMappedCalculation(
                        AppendConstantCalculation.getInstance(),
                        ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(b2)))
                                .leftMapField(b1, inputValue)
                                .rightMapField(outputValue, b2)
                                .build()),
                parametersY);

        SimpleEntity entity = new SimpleEntity(schema);
        entity.setValueOfField(a0, "A");
        entity.setValueOfField(b0, "B");
        ImmutableEntityWithEnrichments entityWitEnrichments = new ImmutableEntityWithEnrichments(
                entity,
                ImmutableList.of(enrichmentA1, enrichmentA2, enrichmentB1, enrichmentB2));

        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(AppendConstantCalculation.getInstance(),
                        new AppendConstantCalculationImplementation()
                                .enableAutoApply())
                .build();
        PipelineEngine engine = new PipelineEngine(calculationFactory, () -> new SimpleEntity(schema),
                MoreExecutors.directExecutor());
        EntityFieldReadWriteAccessor output = engine.process(entityWitEnrichments).get();
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
        int numberOfFields = 200; // TODO if I increase this it sometimes hangs!
        List<Field> fields = new ArrayList<>();
        for (int i = 0; i < numberOfFields; ++i) {
            fields.add(new NamedField("a" + i));
        }
        EntitySchema schema = new NamedEntitySchema("ChainedEntity", fields);
        SimpleEntity parameters = new SimpleEntity(AppendConstantCalculation.parameterSchema);
        parameters.setValueOfField(parameterConstant, ""); // do not append anything real

        List<Enrichment> enrichments = new ArrayList<>();
        for (int i = 1; i < numberOfFields; ++i) {
            Enrichment enrichment = new ImmutableEnrichment(
                    new ImmutableMappedCalculation(
                            AppendConstantCalculation.getInstance(),
                            ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(fields.get(i)))) // TODO why can't this filter be implicit?
                                    .leftMapField(fields.get(i - 1), inputValue)
                                    .rightMapField(outputValue, fields.get(i))
                                    .build()),
                    parameters);
            enrichments.add(enrichment);
        }

        SimpleEntity entity = new SimpleEntity(schema);
        entity.setValueOfField(fields.get(0), "A");

        ImmutableEntityWithEnrichments entityWitEnrichments = new ImmutableEntityWithEnrichments(
                entity,
                enrichments);

        CalculationFactory calculationFactory = CalculationFactory.newBuilder()
                .register(AppendConstantCalculation.getInstance(),
                        new AppendConstantCalculationImplementation()
                                .enableAutoApply())
                .build();
        Stopwatch sw = Stopwatch.createStarted();
        //for (int i = 0; i < 10000; ++i)
        {
            PipelineEngine engine = new PipelineEngine(calculationFactory, () -> new SimpleEntity(schema),
                    MoreExecutors.directExecutor());
            EntityFieldReadWriteAccessor output = engine.process(entityWitEnrichments).get();
            assertThat(output.getValueOfField(fields.get(0)), is("A"));
            assertThat(output.getValueOfField(fields.get(1)), is("A"));
            assertThat(output.getValueOfField(fields.get(numberOfFields - 1)), is("A"));
        }
        System.out.printf("Took: %sms%n", sw.elapsed(TimeUnit.MILLISECONDS));
    }

    // TODO test cancellation

}
