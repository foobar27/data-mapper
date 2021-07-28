package data.refinery.boilerplate;

import data.refinery.testing.AbstractEntityMatcher;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

import java.net.URL;
import java.time.ZonedDateTime;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

public final class DocumentMatcher extends AbstractEntityMatcher {

    // TODO create instances for DocumentPojo, ImmutableDocument etc

    private DocumentMatcher() {
        super(documentSchema());
    }

    public static DocumentMatcher document() {
        return new DocumentMatcher();
    }

    // TODO concrete instances, for mutable, immutable etc

    public DocumentMatcher url(URL value) {
        return url(Matchers.is(value));
    }

    public DocumentMatcher url(Matcher<URL> matcher) {
        registerMatcher(documentSchema().url(), matcher);
        return this;
    }

    public DocumentMatcher publicationDate(ZonedDateTime value) {
        return publicationDate(Matchers.is(value));
    }

    public DocumentMatcher publicationDate(Matcher<ZonedDateTime> matcher) {
        registerMatcher(documentSchema().publicationDate(), matcher);
        return this;
    }

    public DocumentMatcher title(String title) {
        return title(Matchers.is(title));
    }

    public DocumentMatcher title(Matcher<String> matcher) {
        registerMatcher(documentSchema().title(), matcher);
        return this;
    }

    public DocumentMatcher content(String content) {
        return content(Matchers.is(content));
    }

    public DocumentMatcher content(Matcher<String> matcher) {
        registerMatcher(documentSchema().content(), matcher);
        return this;
    }

}
