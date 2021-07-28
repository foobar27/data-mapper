//package data.mapper.testing;
//
//import com.google.common.collect.ArrayListMultimap;
//import data.mapper.schema.EntitySchema;
//import data.mapper.schema.EntityReadAccessor;
//import data.mapper.schema.Field;
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.hamcrest.TypeSafeDiagnosingMatcher;
//
//import java.util.concurrent.atomic.AtomicBoolean;
//
//public class AbstractEntityMatcher<EntityType extends EntitySchema> extends TypeSafeDiagnosingMatcher<EntityReadAccessor> {
//
//    private final EntityType entity;
//    private final ArrayListMultimap<Field<?>, Matcher<?>> matchers = ArrayListMultimap.create();
//
//    public AbstractEntityMatcher(EntityType entity) {
//        this.entity = entity;
//    }
//
//    protected <NativeFieldType, FieldType extends Field<NativeFieldType>> void registerMatcher(FieldType field, Matcher<NativeFieldType> matcher) {
//        matchers.put(field, matcher);
//    }
//
//    @Override
//    protected boolean matchesSafely(EntityReadAccessor item, Description mismatchDescription) {
//        AtomicBoolean success = new AtomicBoolean(true);
//        matchers.forEach((field, matcher) -> {
//            Object value = item.getValueOfField(field);
//            if (!matcher.matches(value)) {
//                success.set(false);
//                mismatchDescription.appendText("field ")
//                        .appendText(field.name())
//                        .appendText(": ");
//                matcher.describeMismatch(value, mismatchDescription);
//            }
//        });
//        return success.get();
//    }
//
//    @Override
//    public void describeTo(Description description) {
//        // TODO
//    }
//
//}
