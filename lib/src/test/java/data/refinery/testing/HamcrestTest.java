package data.mapper.example.test;

import data.refinery.boilerplate.DocumentPojo;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZonedDateTime;

import static data.refinery.boilerplate.DocumentMatcher.document;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

public class HamcrestTest {

    @Test
    public void documentPojoMatchingTest() throws MalformedURLException {
        DocumentPojo doc = new DocumentPojo();
        doc.setUrl(new URL("http://www.example.com/"));
        doc.setPublicationDate(ZonedDateTime.now());
        doc.setTitle("the title");
        doc.setContent("the content");
        assertThat(doc,
                document()
                        .title(containsString("title"))
                        .content(is("the content")));
    }

}
