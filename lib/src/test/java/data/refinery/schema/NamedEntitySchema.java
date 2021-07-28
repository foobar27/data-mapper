package data.refinery.schema;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

public final class NamedEntitySchema implements EntitySchema {

    private final String name;
    private final List<NamedField> fields;

    public static NamedEntitySchema createFromLocalNames(String name, List<String> fieldNames) {
        return new NamedEntitySchema(
                name,
                fieldNames.stream()
                        .map(fieldName -> new NamedField(name + "." + fieldName))
                        .collect(Collectors.toList()));
    }

    public NamedEntitySchema(String name, List<NamedField> fields) {
        this.name = checkNotNull(name);
        this.fields = ImmutableList.copyOf(fields);
    }

    public String getName() {
        return name;
    }

    @Override
    public List<Field> getFields() {
        return fields.stream().map(x -> (Field) x).collect(Collectors.toList());
    }

    public Field getFieldByLocalName(String localFieldName) {
        String globalFieldName = this.name + "." + localFieldName;
        return fields.stream()
                .filter(field -> field.getName().equals(globalFieldName))
                .findFirst()
                .get();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper("NamedEntitySchema")
                .add("name", name)
                .add("fields", fields)
                .toString();
    }

}
