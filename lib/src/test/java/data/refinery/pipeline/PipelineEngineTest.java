package data.refinery.pipeline;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.schema.*;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

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
                ConcatStringsCalculation.getInstance(),
                parameters,
                ProfunctorCalculationTest.fullNameCalculation);
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
                AppendConstantCalculation.getInstance(),
                parametersX,
                ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(a1))) // TODO why can't this filter be explicit?
                        .leftMapField(a0, inputValue)
                        .rightMapField(outputValue, a1)
                        .build());
        Enrichment enrichmentA2 = new ImmutableEnrichment(
                AppendConstantCalculation.getInstance(),
                parametersY,
                ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(a2)))
                        .leftMapField(a1, inputValue)
                        .rightMapField(outputValue, a2)
                        .build());
        Enrichment enrichmentB1 = new ImmutableEnrichment(
                AppendConstantCalculation.getInstance(),
                parametersX,
                ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(b1)))
                        .leftMapField(b0, inputValue)
                        .rightMapField(outputValue, b1)
                        .build());
        Enrichment enrichmentB2 = new ImmutableEnrichment(
                AppendConstantCalculation.getInstance(),
                parametersY,
                ImmutableProfunctorEntityMapping.newBuilder(schema, inputSchema, outputSchema, schema.filterKeys(ImmutableSet.of(b2)))
                        .leftMapField(b1, inputValue)
                        .rightMapField(outputValue, b2)
                        .build());

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
        System.out.println(output.toString());
        assertThat(output.getValueOfField(a0), is("A"));
        assertThat(output.getValueOfField(a1), is("AX"));
        assertThat(output.getValueOfField(a2), is("AXY"));
        assertThat(output.getValueOfField(b0), is("B"));
        assertThat(output.getValueOfField(b1), is("BX"));
        assertThat(output.getValueOfField(b2), is("BXY"));
    }

    // TODO test cancellation

}
