package data.refinery.pipeline;

import com.google.common.collect.ImmutableSet;
import com.google.common.util.concurrent.MoreExecutors;
import data.refinery.boilerplate.ConcatStringsCalculationDefinition;
import data.refinery.mapping.EntityAdapter;
import data.refinery.mapping.ImmutableEntityAdapter;
import data.refinery.schema.EntityFieldReadAccessor;
import data.refinery.schema.SimpleEntity;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

import static data.refinery.boilerplate.PersonSchema.personSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MappedCalculationTest {

    public static final MappedCalculation mappedCalculation = new ImmutableMappedCalculation(
            ConcatStringsCalculationDefinition.getInstance(),
            ImmutableEntityAdapter.newBuilder(
                    personSchema().filterKeys(ImmutableSet.of(personSchema().firstName(), personSchema().lastName())),
                    ConcatStringsCalculationDefinition.inputSchema(),
                    ConcatStringsCalculationDefinition.outputSchema(),
                    personSchema().filterKeys(ImmutableSet.of(personSchema().fullName())))
                    .leftMapField(personSchema().firstName(), ConcatStringsCalculationDefinition.inputSchema().left())
                    .leftMapField(personSchema().lastName(), ConcatStringsCalculationDefinition.inputSchema().right())
                    .rightMapField(ConcatStringsCalculationDefinition.outputSchema().value(), personSchema().fullName())
                    .verifyNormalizeAndBuild());

    @Test
    public void concatenateFirstAndLastName() throws ExecutionException, InterruptedException {
        CalculationImplementation calculation = new ConcatStringsCalculationImplementation(MoreExecutors.directExecutor())
                .enableAutoApply()
                .wrap(mappedCalculation.getMapping());
        SimpleEntity person = new SimpleEntity(personSchema());
        person.setValueOfField(personSchema().firstName(), "John");
        person.setValueOfField(personSchema().lastName(), "Doe");

        ConcatStringsCalculationDefinition.ParametersSchema parametersSchema = ConcatStringsCalculationDefinition.parameterSchema();

        SimpleEntity parameters = new SimpleEntity(parametersSchema);
        parameters.setValueOfField(parametersSchema.middle(), " ");

        EntityFieldReadAccessor output = calculation.apply(person, parameters).get();
        output.mergeInto(person);
        assertThat(person.getValueOfField(personSchema().firstName()), is(equalTo("John")));
        assertThat(person.getValueOfField(personSchema().lastName()), is(equalTo("Doe")));
        assertThat(person.getValueOfField(personSchema().fullName()), is(equalTo("John Doe")));
    }

}
