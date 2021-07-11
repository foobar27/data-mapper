//package data.mapper.example.schema;
//
//import data.mapper.pipeline.AbstractEntityWithStrategies;
//import data.mapper.pipeline.ValueOrStrategy;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//public class DocumentWithStrategies extends AbstractEntityWithStrategies<DocumentSchema> {
//
//    private final ValueOrStrategy<URL> url = registerField(DocumentSchema.Url.getInstance());
//    private final ValueOrStrategy<ZonedDateTime> publicationDate = registerField(DocumentSchema.PublicationDate.getInstance());
//    private final ValueOrStrategy<String> title = registerField(DocumentSchema.Title.getInstance());
//    private final ValueOrStrategy<String> content = registerField(DocumentSchema.Content.getInstance());
//
//    public ValueOrStrategy url() {
//        return url;
//    }
//
//    public ValueOrStrategy publicationDate() {
//        return publicationDate;
//    }
//
//    public ValueOrStrategy title() {
//        return title;
//    }
//
//    public ValueOrStrategy content() {
//        return content;
//    }
//
//}
