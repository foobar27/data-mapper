package com.github.foobar27.datamapper.boilerplate;

import com.google.common.collect.ImmutableSet;
import com.github.foobar27.datamapper.schema.EntityFieldReadAccessor;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.github.foobar27.datamapper.boilerplate.DocumentSchema.documentSchema;

public final class ImmutableDocument implements DocumentReadAccessors {

    private final URL url;
    private final ZonedDateTime publicationDate;
    private final String title;
    private final String content;
    ;
    private final Set<DocumentFlags> flags;

    private ImmutableDocument(Builder builder) {
        this.url = builder.url;
        this.publicationDate = builder.publicationDate;
        this.title = builder.title;
        this.content = builder.content;
        this.flags = ImmutableSet.copyOf(builder.flags);
    }

    @Override
    public URL getUrl() {
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
    public boolean isComment() {
        return flags.contains(DocumentFlags.IsComment);
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

        private URL url;
        private ZonedDateTime publicationDate;
        private String title;
        private String content;
        private Set<DocumentFlags> flags;

        private Builder() {
            this.flags = new HashSet<>();
        }

        private Builder(ImmutableDocument document) {
            this.url = document.url;
            this.publicationDate = document.publicationDate;
            this.title = document.title;
            this.content = document.content;
            this.flags = new HashSet<>(document.flags);
        }

        private Builder(EntityFieldReadAccessor document) {
            this.url = (URL) document.getValueOfField(documentSchema().url());
            this.publicationDate = (ZonedDateTime) document.getValueOfField(documentSchema().publicationDate());
            this.title = (String) document.getValueOfField(documentSchema().title());
            this.content = (String) document.getValueOfField(documentSchema().content());
        }

        @Override
        public URL getUrl() {
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
        public boolean isComment() {
            return flags.contains(DocumentFlags.IsComment);
        }

        @Override
        public void setUrl(URL value) {
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

        @Override
        public void setIsComment(boolean value) {
            if (value) {
                flags.add(DocumentFlags.IsComment);
            } else {
                flags.remove(DocumentFlags.IsComment);
            }
        }

        public ImmutableDocument build() {
            return new ImmutableDocument(this);
        }
    }

    // TODO toString, hashCode, equals

}
