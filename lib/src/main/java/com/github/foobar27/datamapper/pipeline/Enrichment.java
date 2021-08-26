package com.github.foobar27.datamapper.pipeline;

import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntityFieldReadWriteAccessor;
import com.github.foobar27.datamapper.schema.Field;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Enrichment {

    MappedCalculation getMappedCalculation();

    EntityFieldReadAccessor getParameters();

    default CompletableFuture<EntityFieldReadAccessor> apply(EntityFieldReadWriteAccessor result, CalculationFactory calculationFactory) {
        return calculationFactory.apply(getMappedCalculation().getCalculation())
                .wrap(getMappedCalculation().getMapping())
                .apply(result, getParameters());
    }

    default List<Field> getInputFields() {
        return getMappedCalculation()
                .getMapping()
                .getLeftMapping()
                .getInputSchema()
                .getFields();
    }

    default List<Field> getOutputFields() {
        return getMappedCalculation()
                .getMapping()
                .getRightMapping()
                .getOutputSchema()
                .getFields();
    }

}
