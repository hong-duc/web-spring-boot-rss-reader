package dman.hongduc.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Lớp thể hiện 1 RSS feed
 *
 * @author duc
 */
public class Article implements Serializable, Comparable<Article> {

    @XmlElement
    private final String title;
    @XmlElement
    private final String link;
    @JsonDeserialize(using = CustomDateDeserializer.class)
    @JsonSerialize(using = CustomDateSerializer.class)
    @XmlJavaTypeAdapter(FeedXmlWrapper.class)
    private final LocalDate publishDate;
    @XmlElement
    private final boolean read;

    public Article() {
        this("", "", LocalDate.now(), false);
    }

    public Article(String title, String link, LocalDate publishDate, boolean read) {
        this.title = title;
        this.link = link;
        this.publishDate = publishDate;
        this.read = read;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the link
     */
    public String getLink() {
        return link;
    }

    /**
     * @return the read
     */
    public boolean isRead() {
        return this.read;
    }

    /**
     * @return the publishDate
     */
    public LocalDate getPublishDate() {
        return this.publishDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Article) {
            Article a = (Article) obj;
            boolean eq1 = this.getTitle().equals(a.getTitle());
            boolean eq2 = this.getLink().equals(a.getLink());
            boolean eq3 = this.getPublishDate().equals(a.getPublishDate());
            return eq1 && eq2 && eq3;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash += 97 * hash + Objects.hashCode(this.getTitle());
        hash += 97 * hash + Objects.hashCode(this.getLink());
        hash += 97 * hash + Objects.hashCode(this.getPublishDate());
        return hash;
    }

    @Override
    public int compareTo(Article o) {
        int number = this.getPublishDate().compareTo(o.getPublishDate());
        if (number == 0) {
            return this.getTitle().compareTo(o.getTitle());
        } else {
            return number * -1;
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        return builder.append("{")
                .append("title:")
                .append(this.title)
                .append(",")
                .append("link:")
                .append(this.link)
                .append(",")
                .append("read:")
                .append(this.read)
                .append(",")
                .append("publishDate:")
                .append(this.publishDate.toString())
                .append("}")
                .toString();
    }

}
