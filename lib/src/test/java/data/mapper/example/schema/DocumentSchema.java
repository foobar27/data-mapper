//package data.mapper.example.schema;
//
//import data.mapper.schema.AbstractEntitySchema;
//import data.mapper.schema.AbstractField;
//import data.mapper.schema.Field;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//public final class DocumentSchema extends AbstractEntitySchema {
//
//    private static final DocumentSchema instance = new DocumentSchema();
//
//    public static DocumentSchema getInstance() {
//        return instance;
//    }
//
//    public static DocumentPathBuilder getPathBuilder() {
//        return DocumentPathBuilder.getInstance();
//    }
//
//    private final Field<ZonedDateTime> publicationDate = registerField(PublicationDate.getInstance());
//
//    private DocumentSchema() {
//        // disable public constructor
//    }
//
//    public Field<ZonedDateTime> publicationDate() {
//        return publicationDate;
//    }
//
//    @Override
//    public String name() {
//        return "Document";
//    }
//
//    // Nested Fields
//
//    public static final class Url extends AbstractField<URL> {
//
//        private static final Url instance = new Url();
//
//        public static Url getInstance() {
//            return instance;
//        }
//
//        private Url() {
//            // disable public constructor
//        }
//
//        @Override
//        public String name() {
//            return "url";
//        }
//
//    }
//
//    public static final class PublicationDate extends AbstractField<ZonedDateTime> {
//
//        private static final PublicationDate instance = new PublicationDate();
//
//        public static PublicationDate getInstance() {
//            return instance;
//        }
//
//        private PublicationDate() {
//            // disable public constructor
//        }
//
//        @Override
//        public String name() {
//            return "publicationDate";
//        }
//
//    }
//
//
//    public static final class Title extends AbstractField<String> {
//
//        private static final Title instance = new Title();
//
//        public static Title getInstance() {
//            return instance;
//        }
//
//        private Title() {
//            // disable public constructor
//        }
//
//        @Override
//        public String name() {
//            return "title";
//        }
//
//    }
//
//    public static final class Content extends AbstractField<String> {
//
//        private static final Content instance = new Content();
//
//        public static Content getInstance() {
//            return instance;
//        }
//
//        private Content() {
//            // disable public constructor
//        }
//
//        @Override
//        public String name() {
//            return "content";
//        }
//    }
//
//}
