//package data.mapper.example.schema;
//
//import com.google.common.base.MoreObjects;
//import data.mapper.schema.EntityReadWriteAccessor;
//import data.mapper.schema.Field;
//
//import java.net.URL;
//import java.time.ZonedDateTime;
//import java.util.Objects;
//
//public final class MutableDocument implements EntityReadWriteAccessor {
//
//    private URL url;
//    private ZonedDateTime publicationDate;
//    private String title;
//    private String content;
//
//    @Override
//    public <NativeType, FieldType extends Field<NativeType>> NativeType getValueOfField(FieldType field) {
//        // TODO if we register the fields, this code is not necessary anymore
//        if (field == DocumentSchema.Url.getInstance()) {
//            return (NativeType) getUrl();
//        } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//            return (NativeType) this.getPublicationDate();
//        } else if (field == DocumentSchema.Title.getInstance()) {
//            return (NativeType) this.getTitle();
//        } else if (field == DocumentSchema.Content.getInstance()) {
//            return (NativeType) this.getContent();
//        }
//        throw new IllegalArgumentException(String.format("Unknown field in MutableDocument: %s", field));
//    }
//
//    @Override
//    public <NativeType, FieldType extends Field<NativeType>> void setValueOfField(FieldType field, NativeType
//            value) {
//        if (field == DocumentSchema.Url.getInstance()) {
//            this.setUrl((URL) value);
//        } else if (field == DocumentSchema.PublicationDate.getInstance()) {
//            MutableDocument.this.setPublicationDate((ZonedDateTime) value);
//        } else if (field == DocumentSchema.Title.getInstance()) {
//            MutableDocument.this.setTitle((String) value);
//        } else if (field == DocumentSchema.Content.getInstance()) {
//            MutableDocument.this.setContent((String) value);
//        }
//        throw new IllegalArgumentException(String.format("Unknown field in MutableDocument: %s", field));
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(url, publicationDate, title, content);
//    }
//
//    @Override
//    public boolean equals(Object t) {
//        if (!(t instanceof MutableDocument)) {
//            return false;
//        }
//        MutableDocument that = (MutableDocument) t;
//        return Objects.equals(url, that.url)
//                && Objects.equals(publicationDate, that.publicationDate)
//                && Objects.equals(title, that.title)
//                && Objects.equals(content, that.content);
//    }
//
//    @Override
//    public String toString() {
//        return MoreObjects.toStringHelper("Document")
//                .add("url", url)
//                .add("publicationDate", publicationDate)
//                .add("title", title)
//                .add("content", content)
//                .toString();
//    }
//
//    public URL getUrl() {
//        return url;
//    }
//
//    public void setUrl(URL url) {
//        this.url = url;
//    }
//
//    public ZonedDateTime getPublicationDate() {
//        return publicationDate;
//    }
//
//    public void setPublicationDate(ZonedDateTime publicationDate) {
//        this.publicationDate = publicationDate;
//    }
//
//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getContent() {
//        return content;
//    }
//
//    public void setContent(String content) {
//        this.content = content;
//    }
//
//}