package dman.hongduc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Lớp thể hiện 1 RSS feed
 *
 * @author duc
 */
public class Article implements Serializable, Comparable<Article> {

    private String title;
    private String link;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    private Optional<LocalDate> publishDate;

    public Article() {
        this.title = "";
        this.link = "";
        this.publishDate = Optional.empty();
    }

    public Article(String title, String link, LocalDate publishDate) {
        this.title = title;
        this.link = link;
        this.publishDate = Optional.of(publishDate);
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @param link the link to set
     */
    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Article) {
            Article article = (Article) obj;
            return this.getLink().equalsIgnoreCase(article.getLink()) && this.getTitle().equalsIgnoreCase(article.getTitle()) && this.getPublishDate().equals(article.getPublishDate());
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.getTitle());
        hash = 97 * hash + Objects.hashCode(this.getLink());
        hash = 97 * hash + Objects.hashCode(this.getPublishDate());
        return hash;
    }

    @Override
    public int compareTo(Article o) {
        if (o.getPublishDate().isAfter(this.getPublishDate())) {
            return 1;
        }
        else{
            return -1;
        }
    }

    /**
     * @return the publishDate
     */
    @XmlJavaTypeAdapter(FeedXmlWrapper.class)
    public LocalDate getPublishDate() {
        return publishDate.orElse(LocalDate.of(2000, 1, 1));
    }

    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = Optional.ofNullable(publishDate);
    }

}
