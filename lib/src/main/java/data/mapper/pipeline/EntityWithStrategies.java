package data.mapper.pipeline;

import data.mapper.schema.EntitySchema;
import data.mapper.schema.Field;

import java.util.Map;

// TODO remove (migrated)
public interface EntityWithStrategies<Schema extends EntitySchema> {

    Map<Field<?>, ValueOrStrategy<?>> getAllFields();

}
