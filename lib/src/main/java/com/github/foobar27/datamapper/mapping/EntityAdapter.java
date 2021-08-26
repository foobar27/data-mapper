package com.github.foobar27.datamapper.mapping;

public interface EntityAdapter {

    /**
     * Gets the mapping from the relevant subset of the entity to the input of the calculation.
     */
    EntityMapping getLeftMapping();

    /**
     * Gets the mapping from the output of the calculation to the relevant subset of the entity.
     */
    EntityMapping getRightMapping();

}
