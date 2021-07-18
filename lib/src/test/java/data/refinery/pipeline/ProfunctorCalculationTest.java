package data.refinery.pipeline;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.mapping.ImmutableProfunctorEntityMapping;
import data.refinery.mapping.ProfunctorEntityMapping;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static data.refinery.example.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

// TODO shouldn't this be EnrichmentTest?
public class ProfunctorCalculationTest {

    public static final ProfunctorEntityMapping fullNameCalculation = ImmutableProfunctorEntityMapping.newBuilder(
            personSchema().filterKeys(ImmutableSet.of(personSchema().firstName(), personSchema().lastName())),
            ConcatStringsCalculation.inputSchema(),
            ConcatStringsCalculation.outputSchema(),
            personSchema().filterKeys(ImmutableSet.of(personSchema().fullName())))
            .leftMapField(personSchema().firstName(), ConcatStringsCalculation.inputSchema().left())
            .leftMapField(personSchema().lastName(), ConcatStringsCalculation.inputSchema().right())
            .rightMapField(ConcatStringsCalculation.outputSchema().value(), personSchema().fullName())
            .verifyNormalizeAndBuild();

    @Test
    public void concatenateFirstAndLastName() throws ExecutionException, InterruptedException {
        CalculationImplementation calculation = new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                .enableAutoApply()
                .wrap(fullNameCalculation);
        SimpleEntity person = new SimpleEntity(personSchema());
        person.setValueOfField(personSchema().firstName(), "John");
        person.setValueOfField(personSchema().lastName(), "Doe");

        ConcatStringsCalculation.ParametersSchema parametersSchema = ConcatStringsCalculation.parameterSchema();

        SimpleEntity parameters = new SimpleEntity(parametersSchema);
        parameters.setValueOfField(parametersSchema.middle(), " ");

        EntityFieldReadAccessor output = calculation.apply(person, parameters).get();
        output.mergeInto(person);
        assertThat(person.getValueOfField(personSchema().firstName()), is(equalTo("John")));
        assertThat(person.getValueOfField(personSchema().lastName()), is(equalTo("Doe")));
        assertThat(person.getValueOfField(personSchema().fullName()), is(equalTo("John Doe")));
    }

}
