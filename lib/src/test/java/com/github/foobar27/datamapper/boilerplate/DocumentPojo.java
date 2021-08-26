package com.github.foobar27.datamapper.boilerplate;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;

public final class DocumentPojo implements DocumentReadWriteAccessors {

    private URL url;
    private ZonedDateTime publicationDate;
    private String title;
    private String content;
    private HashSet<DocumentFlags> flags = new HashSet<>();

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

    // TODO toString, hashCode, equals
}
