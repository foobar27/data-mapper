package data.refinery.boilerplate;

import java.net.URI;
import java.time.ZonedDateTime;

public final class DocumentPojo implements DocumentReadWriteAccessors {

    private URI url;
    private ZonedDateTime publicationDate;
    private String title;
    private String content;

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

    // TODO toString, hashCode, equals
}
