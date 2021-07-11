package data.refinery.schema;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.util.Set;

import static com.google.common.base.Preconditions.checkNotNull;

final class FilteredEntityFieldReadAccessor implements EntityFieldReadAccessor {

    private final EntityFieldReadAccessor delegate;
    private final Set<Field> fields;

    public static EntityFieldReadAccessor filterFields(EntityFieldReadAccessor delegate, Set<Field> fields) {
        if (fields.isEmpty()) {
            return EmptyEntityFieldReadAccessor.getInstance();
        }
        return new FilteredEntityFieldReadAccessor(delegate, fields);
    }

    private FilteredEntityFieldReadAccessor(EntityFieldReadAccessor delegate, Set<Field> fields) {
        this.delegate = checkNotNull(delegate);
        this.fields = ImmutableSet.copyOf(fields);
    }

    @Override
    public EntitySchema getSchema() {
        return delegate.getSchema().filterKeys(fields);
    }

    @Override
    public Object getValueOfField(Field field) {
        if (fields.contains(field)) {
            return delegate.getValueOfField(field);
        }
        throw new NoSuchFieldException(getSchema(), field);
    }

    @Override
    public EntityFieldReadAccessor filterFields(Set<Field> fields) {
        return filterFields(delegate, Sets.intersection(this.fields, fields));
    }
}
