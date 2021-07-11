//package data.mapper.example.test;
//
//import data.mapper.example.schema.MutableDocument;
//import org.junit.jupiter.api.Test;
//
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//import static data.mapper.example.schema.DocumentMatcher.document;
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.is;
//
//public class HamcrestTest {
//
//    @Test
//    public void mutableDocument() throws MalformedURLException {
//        MutableDocument doc = new MutableDocument();
//        doc.setUrl(new URL("http://www.example.com/"));
//        doc.setPublicationDate(ZonedDateTime.now());
//        doc.setTitle("the title");
//        doc.setContent("the content");
//        assertThat(doc,
//                document()
//                        .title(is("the title")));
//    }
//
//}
