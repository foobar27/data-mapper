# Nomenclature

- Field: a field of an entity; must be unique (e.g. two different
  entities cannot share the same fields)
- Entity: composed of multiple fields
- Calculation: a description of a calculation with zero or more inputs 
  and one or more outputs. Calculations can be serialized and stored
  in a database.
- CalculationImplementation: a concrete implementation of the calculation
- Enrichment: a calculation with a concrete mapping from/to the fields of an entity
  input and output fields to the fields of an entity
- EntityMapping: reversible mapping
- Profunctor: wrapper for a calculation (which maps entity fields to
  the input, and output fields to the entity fields)
