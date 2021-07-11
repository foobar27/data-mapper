package data.mapper.schema;

// TODO remove (migrated)
public interface EntityReadAccessor<Schema extends EntitySchema> {

    <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field);

}
