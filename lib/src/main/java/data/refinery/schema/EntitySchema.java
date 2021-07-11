package data.refinery.schema;

import java.util.List;
import java.util.Set;

public interface EntitySchema {

    List<Field> getFields();

    default EntitySchema filterKeys(Set<Field> fields) {
        return FilteredEntitySchema.filterKeys(this, fields);
    }
}
