package data.mapper.schema;

import java.util.List;

// TODO remove (migrated)
public interface EntitySchema {

    String name();

    List<Field<?>> getAllFields();

}
