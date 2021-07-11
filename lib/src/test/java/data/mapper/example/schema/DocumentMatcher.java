//package data.mapper.example.schema;
//
//import data.mapper.testing.AbstractEntityMatcher;
//import org.hamcrest.Matcher;
//import org.hamcrest.Matchers;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//public class DocumentMatcher extends AbstractEntityMatcher<DocumentSchema> {
//
//    private DocumentMatcher() {
//        super(DocumentSchema.getInstance());
//    }
//
//    public static DocumentMatcher document() {
//        return new DocumentMatcher();
//    }
//
//    // TODO concrete instances, for mutable, immutable etc
//
//    public DocumentMatcher url(URL value) {
//        return url(Matchers.is(value));
//    }
//
//    public DocumentMatcher url(Matcher<URL> matcher) {
//        registerMatcher(DocumentSchema.Url.getInstance(), matcher);
//        return this;
//    }
//
//    public DocumentMatcher publicationDate(ZonedDateTime value) {
//        return publicationDate(Matchers.is(value));
//    }
//
//    public DocumentMatcher publicationDate(Matcher<ZonedDateTime> matcher) {
//        registerMatcher(DocumentSchema.PublicationDate.getInstance(), matcher);
//        return this;
//    }
//
//    public DocumentMatcher title(String title) {
//        return title(Matchers.is(title));
//    }
//
//    public DocumentMatcher title(Matcher<String> matcher) {
//        registerMatcher(DocumentSchema.Title.getInstance(), matcher);
//        return this;
//    }
//
//
//}
