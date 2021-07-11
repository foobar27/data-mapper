package data.mapper.schema;

// TODO remove (migrated)
public abstract class AbstractField<NativeType> implements Field<NativeType> {

    @Override
    public String toString() {
        return "Field[" + name() + "]";
    }

}
