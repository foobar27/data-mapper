# Nomenclature

- Field: a field of an entity; must be unique (e.g. two different
  entities cannot share the same fields)
- Entity: composed of multiple fields
- CalculationDefinition: a description of a calculationDefinition with zero or more inputs 
  and one or more outputs. Calculations can be serialized and stored
  in a database.
- MappedCalculation: a Calculation with a concrete mapping from/to the
  fields of an entity input and output fields to the fields of an
  entity
- CalculationImplementation: a concrete implementation of the calculationDefinition
- TypedCalculationImplementation: a CalculationImplementation bound to concrete types
- EnrichedCalculation: a MappedCalculation with the parameters
- Enrichment: a calculationDefinition 
- EntityMapping: reversible mapping
- Adapter: wrapper for a calculationDefinition (which maps entity fields to
  the input, and output fields to the entity fields)
