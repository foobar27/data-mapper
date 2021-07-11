package data.mapper.schema;

public interface EntityWriteAccessor<Schema extends EntitySchema> {

    <NativeType, FieldType extends Field<NativeType>> void setValueOfField(FieldType field, NativeType value);

}
