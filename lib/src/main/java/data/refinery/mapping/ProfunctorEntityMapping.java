package data.refinery.mapping;

// TODO is this really a Profunctor? not an End?
public interface ProfunctorEntityMapping {

    /**
     * Gets the mapping from the relevant subset of the entity to the input of the calculation.
     */
    EntityMapping getLeftMapping();

    /**
     * Gets the mapping from the output of the calculation to the relevant subset of the entity.
     */
    EntityMapping getRightMapping();

}
