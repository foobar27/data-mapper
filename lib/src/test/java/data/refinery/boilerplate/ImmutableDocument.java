package data.refinery.boilerplate;

import data.refinery.schema.EntityFieldReadAccessor;

import java.net.URI;
import java.time.ZonedDateTime;

import static data.refinery.boilerplate.DocumentSchema.documentSchema;

public final class ImmutableDocument implements DocumentReadAccessors {

    private final URI url;
    private final ZonedDateTime publicationDate;
    private final String title;
    private final String content;

    private ImmutableDocument(Builder builder) {
        this.url = builder.url;
        this.publicationDate = builder.publicationDate;
        this.title = builder.title;
        this.content = builder.content;
    }

    @Override
    public URI getUrl() {
        return url;
    }

    @Override
    public ZonedDateTime getPublicationDate() {
        return publicationDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getContent() {
        return content;
    }

    // TODO toString, hashCode, equals

    public static Builder newBuilder() {
        return new Builder();
    }

    public static Builder newBuilder(EntityFieldReadAccessor other) {
        return new Builder(other);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static final class Builder implements DocumentReadWriteAccessors {

        private URI url;
        private ZonedDateTime publicationDate;
        private String title;
        private String content;

        private Builder() {
            // nothing to do
        }

        private Builder(ImmutableDocument document) {
            this.url = document.url;
            this.publicationDate = document.publicationDate;
            this.title = document.title;
            this.content = document.content;
        }

        private Builder(EntityFieldReadAccessor document) {
            this.url = (URI) document.getValueOfField(documentSchema().url());
            this.publicationDate = (ZonedDateTime) document.getValueOfField(documentSchema().publicationDate());
            this.title = (String) document.getValueOfField(documentSchema().title());
            this.content = (String) document.getValueOfField(documentSchema().content());
        }

        @Override
        public URI getUrl() {
            return url;
        }

        @Override
        public ZonedDateTime getPublicationDate() {
            return publicationDate;
        }

        @Override
        public String getTitle() {
            return title;
        }

        @Override
        public String getContent() {
            return content;
        }

        @Override
        public void setUrl(URI value) {
            this.url = value;
        }

        @Override
        public void setPublicationDate(ZonedDateTime value) {
            this.publicationDate = value;
        }

        @Override
        public void setTitle(String value) {
            this.title = value;
        }

        @Override
        public void setContent(String value) {
            this.content = value;
        }

        public ImmutableDocument build() {
            return new ImmutableDocument(this);
        }
    }

    // TODO toString, hashCode, equals

}
