package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.conversion.EntityFactory;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;
import com.github.foobar27.datamapper.schema.Field;

import java.util.Set;
import java.util.concurrent.Executor;

public final class PipelineEngineFactory {

    public <OutputType extends EntityFieldReadAccessor, OutputBuilderType extends EntityFieldReadWriteAccessor> PipelineEngine<OutputType, OutputBuilderType> createPipelineEngine(
            PipelineDefinition enrichments,
            CalculationFactory calculationFactory,
            EntityFactory<OutputType, OutputBuilderType> outputFactory,
            Executor executor) {
        if (enrichments.getEnrichments().isEmpty()) {
            return new DummyPipelineEngine<>(outputFactory);
        }
//        if (areIndependent(enrichments)) {
//            return new IndependentEnrichmentPipeline(enrichments, calculationFactory, outputFactory, executor);
//        }
        return new GenericPipelineEngine<>(enrichments, calculationFactory, outputFactory, executor);
    }

    boolean areIndependent(PipelineDefinition enrichments) {
        Set<Field> inputFields = enrichments.getAllInputFields();
        Set<Field> outputFields = enrichments.getAllOutputFields();
        return inputFields
                .stream()
                .noneMatch(outputFields::contains);
    }

}