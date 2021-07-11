package data.refinery.schema;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NamedEntitySchema implements EntitySchema {

    private final String name;
    private final List<Field> fields;

    public NamedEntitySchema(String name, List<Field> fields) {
        this.name = checkNotNull(name);
        this.fields = ImmutableList.copyOf(fields);
    }

    public String getName() {
        return name;
    }

    @Override
    public List<Field> getFields() {
        return fields;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("NamedEntitySchema")
                .add("name", name)
                .add("fields", fields)
                .toString();
    }

}
