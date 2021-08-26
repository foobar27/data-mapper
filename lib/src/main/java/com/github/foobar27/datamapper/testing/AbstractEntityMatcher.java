package com.github.foobar27.datamapper.testing;

import com.google.common.collect.ArrayListMultimap;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;
import com.github.foobar27.datamapper.schema.EntitySchema;
import com.github.foobar27.datamapper.schema.Field;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractEntityMatcher extends TypeSafeDiagnosingMatcher<EntityFieldReadAccessor> {

    private final EntitySchema entitySchema;
    private final ArrayListMultimap<Field, Matcher<?>> matchers = ArrayListMultimap.create();

    public AbstractEntityMatcher(EntitySchema entitySchema) {
        this.entitySchema = entitySchema;
    }

    protected void registerMatcher(Field field, Matcher<?> matcher) {
        matchers.put(field, matcher);
    }

    @Override
    protected boolean matchesSafely(EntityFieldReadAccessor item, Description mismatchDescription) {
        AtomicBoolean success = new AtomicBoolean(true);
        matchers.forEach((field, matcher) -> {
            Object value = item.getValueOfField(field);
            if (!matcher.matches(value)) {
                success.set(false);
                mismatchDescription.appendText("field ")
                        .appendText(field.toString())
                        .appendText(": ");
                matcher.describeMismatch(value, mismatchDescription);
            }
        });
        return success.get();
    }

    @Override
    public void describeTo(Description description) {
        // TODO
    }

}
