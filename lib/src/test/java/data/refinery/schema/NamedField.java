package data.refinery.schema;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NamedField implements Field {

    private final String name;

    public NamedField(String name) {
        this.name = checkNotNull(name);
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Field[" + name + "]";
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object t) {
        if (!(t instanceof NamedField)) {
            return false;
        }
        NamedField that = (NamedField) t;
        return this.name.equals(that.name);
    }

}
