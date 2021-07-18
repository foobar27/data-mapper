package data.refinery.pipeline;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static data.refinery.pipeline.ConcatStringsCalculation.*;
import static data.refinery.schema.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

// TODO shouldn't this be EnrichmentTest?
public class ProfunctorCalculationTest {

    public static final ProfunctorEntityMapping fullNameCalculation = ImmutableProfunctorEntityMapping.newBuilder(
            personSchema().filterKeys(ImmutableSet.of(personSchema().firstName(), personSchema().lastName())),
            inputSchema,
            outputSchema,
            personSchema().filterKeys(ImmutableSet.of(personSchema().fullName())))
            .leftMapField(personSchema().firstName(), inputLeft)
            .leftMapField(personSchema().lastName(), inputRight)
            .rightMapField(outputValue, personSchema().fullName())
            .verifyNormalizeAndBuild();

    @Test
    public void concatenateFirstAndLastName() throws ExecutionException, InterruptedException {
        CalculationImplementation calculation = new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                .enableAutoApply()
                .wrap(fullNameCalculation);
        SimpleEntity person = new SimpleEntity(personSchema());
        person.setValueOfField(personSchema().firstName(), "John");
        person.setValueOfField(personSchema().lastName(), "Doe");

        SimpleEntity parameters = new SimpleEntity(parameterSchema);
        parameters.setValueOfField(parametersMiddle, " ");

        EntityFieldReadAccessor output = calculation.apply(person, parameters).get();
        output.mergeInto(person);
        assertThat(person.getValueOfField(personSchema().firstName()), is(equalTo("John")));
        assertThat(person.getValueOfField(personSchema().lastName()), is(equalTo("Doe")));
        assertThat(person.getValueOfField(personSchema().fullName()), is(equalTo("John Doe")));
    }

}
