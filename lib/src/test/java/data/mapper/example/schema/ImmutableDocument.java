//package data.mapper.example.schema;
//
//import data.mapper.schema.EntityReadAccessor;
//import data.mapper.schema.EntityReadWriteAccessor;
//import data.mapper.schema.Field;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//public class ImmutableDocument implements EntityReadAccessor {
//
//    private final URL url;
//    private final ZonedDateTime publicationDate;
//    private final String content;
//    private final String title;
//
//    private ImmutableDocument(Builder builder) {
//        this.url = builder.url;
//        this.publicationDate = builder.publicationDate;
//        this.content = builder.content;
//        this.title = builder.title;
//    }
//
//    @Override
//    public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
//        if (field == DocumentSchema.Url.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getUrl();
//        } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getPublicationDate();
//        } else if (field == DocumentSchema.Title.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getTitle();
//        } else if (field == DocumentSchema.Content.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getContent();
//        }
//        throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//    }
//
//
//    public URL getUrl() {
//        return url;
//    }
//
//    public ZonedDateTime getPublicationDate() {
//        return publicationDate;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    // TODO hashCode, equals, toString
//
//    public static Builder newBuilder() {
//        return new Builder();
//    }
//
//    public Builder toBuilder() {
//        return new Builder(this);
//    }
//
//    public static final class Builder implements EntityReadWriteAccessor {
//
//        private URL url;
//        private ZonedDateTime publicationDate;
//        private String content;
//        private String title;
//
//        private Builder() {
//            // nothing to do
//        }
//
//        private Builder(ImmutableDocument document) {
//            this.url = document.url;
//            this.publicationDate = document.publicationDate;
//            this.content = document.content;
//            this.title = document.content;
//        }
//
//        @Override
//        public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
//            if (field == DocumentSchema.Url.getInstance()) {
//                return (NativeType) Builder.this.getUrl();
//            } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//                return (NativeType) Builder.this.getPublicationDate();
//            } else if (field == DocumentSchema.Title.getInstance()) {
//                return (NativeType) Builder.this.getTitle();
//            } else if (field == DocumentSchema.Content.getInstance()) {
//                return (NativeType) Builder.this.getContent();
//            }
//            throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//        }
//
//        @Override
//        public <NativeType, FieldType extends Field<NativeType>> void setValueOfField(FieldType field, NativeType
//                value) {
//            if (field == DocumentSchema.Url.getInstance()) {
//                Builder.this.setUrl((URL) value);
//            } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//                Builder.this.setPublicationDate((ZonedDateTime) value);
//            } else if (field == DocumentSchema.Title.getInstance()) {
//                Builder.this.setTitle((String) value);
//            } else if (field == DocumentSchema.Content.getInstance()) {
//                Builder.this.setContent((String) value);
//            }
//            throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//        }
//
//        public URL getUrl() {
//            return url;
//        }
//
//        public Builder setUrl(URL value) {
//            this.url = value;
//            return this;
//        }
//
//        public ZonedDateTime getPublicationDate() {
//            return publicationDate;
//        }
//
//        public Builder setPublicationDate(ZonedDateTime value) {
//            this.publicationDate = value;
//            return this;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public Builder setContent(String value) {
//            this.content = value;
//            return this;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public Builder setTitle(String value) {
//            this.title = value;
//            return this;
//        }
//
//        // TODO hashCode, equals, toString
//
//        public ImmutableDocument build() {
//            return new ImmutableDocument(this);
//        }
//
//    }
//
//
//}
//package data.mapper.example.schema;
//
//import data.mapper.schema.EntityReadAccessor;
//import data.mapper.schema.EntityReadWriteAccessor;
//import data.mapper.schema.Field;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//
//public class ImmutableDocument implements EntityReadAccessor {
//
//    private final URL url;
//    private final ZonedDateTime publicationDate;
//    private final String content;
//    private final String title;
//
//    private ImmutableDocument(Builder builder) {
//        this.url = builder.url;
//        this.publicationDate = builder.publicationDate;
//        this.content = builder.content;
//        this.title = builder.title;
//    }
//
//    @Override
//    public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
//        if (field == DocumentSchema.Url.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getUrl();
//        } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getPublicationDate();
//        } else if (field == DocumentSchema.Title.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getTitle();
//        } else if (field == DocumentSchema.Content.getInstance()) {
//            return (NativeType) ImmutableDocument.this.getContent();
//        }
//        throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//    }
//
//
//    public URL getUrl() {
//        return url;
//    }
//
//    public ZonedDateTime getPublicationDate() {
//        return publicationDate;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    // TODO hashCode, equals, toString
//
//    public static Builder newBuilder() {
//        return new Builder();
//    }
//
//    public Builder toBuilder() {
//        return new Builder(this);
//    }
//
//    public static final class Builder implements EntityReadWriteAccessor {
//
//        private URL url;
//        private ZonedDateTime publicationDate;
//        private String content;
//        private String title;
//
//        private Builder() {
//            // nothing to do
//        }
//
//        private Builder(ImmutableDocument document) {
//            this.url = document.url;
//            this.publicationDate = document.publicationDate;
//            this.content = document.content;
//            this.title = document.content;
//        }
//
//        @Override
//        public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
//            if (field == DocumentSchema.Url.getInstance()) {
//                return (NativeType) Builder.this.getUrl();
//            } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//                return (NativeType) Builder.this.getPublicationDate();
//            } else if (field == DocumentSchema.Title.getInstance()) {
//                return (NativeType) Builder.this.getTitle();
//            } else if (field == DocumentSchema.Content.getInstance()) {
//                return (NativeType) Builder.this.getContent();
//            }
//            throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//        }
//
//        @Override
//        public <NativeType, FieldType extends Field<NativeType>> void setValueOfField(FieldType field, NativeType
//                value) {
//            if (field == DocumentSchema.Url.getInstance()) {
//                Builder.this.setUrl((URL) value);
//            } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//                Builder.this.setPublicationDate((ZonedDateTime) value);
//            } else if (field == DocumentSchema.Title.getInstance()) {
//                Builder.this.setTitle((String) value);
//            } else if (field == DocumentSchema.Content.getInstance()) {
//                Builder.this.setContent((String) value);
//            }
//            throw new IllegalArgumentException(String.format("The entity Document does not have a field %s", field));
//        }
//
//        public URL getUrl() {
//            return url;
//        }
//
//        public Builder setUrl(URL value) {
//            this.url = value;
//            return this;
//        }
//
//        public ZonedDateTime getPublicationDate() {
//            return publicationDate;
//        }
//
//        public Builder setPublicationDate(ZonedDateTime value) {
//            this.publicationDate = value;
//            return this;
//        }
//
//        public String getContent() {
//            return content;
//        }
//
//        public Builder setContent(String value) {
//            this.content = value;
//            return this;
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public Builder setTitle(String value) {
//            this.title = value;
//            return this;
//        }
//
//        // TODO hashCode, equals, toString
//
//        public ImmutableDocument build() {
//            return new ImmutableDocument(this);
//        }
//
//    }
//
//
//}
