package data.refinery.pipeline;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.ExampleSchemata;
import data.refinery.schema.SimpleEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static data.refinery.pipeline.ConcatStringsCalculation.*;
import static data.refinery.schema.ExampleSchemata.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

// TODO shouldn't this be EnrichmentTest?
public class ProfunctorCalculationTest {

    public static final ProfunctorEntityMapping fullNameCalculation = ImmutableProfunctorEntityMapping.newBuilder(
            person.filterKeys(ImmutableSet.of(personFirstName, personLastName)),
            inputSchema,
            outputSchema,
            person.filterKeys(ImmutableSet.of(personFullName)))
            .leftMapField(personFirstName, inputLeft)
            .leftMapField(personLastName, inputRight)
            .rightMapField(outputValue, personFullName)
            .build();

    @Test
    public void concatenateFirstAndLastName() throws ExecutionException, InterruptedException {
        CalculationImplementation calculation = new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                .enableAutoApply()
                .wrap(fullNameCalculation);
        SimpleEntity person = new SimpleEntity(ExampleSchemata.person);
        person.setValueOfField(personFirstName, "John");
        person.setValueOfField(personLastName, "Doe");

        SimpleEntity parameters = new SimpleEntity(parameterSchema);
        parameters.setValueOfField(parametersMiddle, " ");

        EntityFieldReadAccessor output = calculation.apply(person, parameters).get();
        output.mergeInto(person);
        assertThat(person.getValueOfField(personFirstName), is(equalTo("John")));
        assertThat(person.getValueOfField(personLastName), is(equalTo("Doe")));
        assertThat(person.getValueOfField(personFullName), is(equalTo("John Doe")));
    }

}
