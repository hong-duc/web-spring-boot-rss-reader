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
    private boolean isRead;
   

    public Article() {
        this.title = "";
        this.link = "";
        this.publishDate = Optional.empty();
        this.isRead = false;
    }

    public Article(String title, String link, LocalDate publishDate, boolean isRead) {
        this.title = title;
        this.link = link;
        this.publishDate = Optional.of(publishDate);
        this.isRead = isRead;
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

    public boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Article){
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
        hash = 97 * hash + Objects.hashCode(this.getTitle());
        hash = 97 * hash + Objects.hashCode(this.getLink());
        hash = 97 * hash + Objects.hashCode(this.getPublishDate());
        return hash;
    }

    @Override
    public int compareTo(Article o) {
        int number = this.getPublishDate().compareTo(o.getPublishDate());
        if(number == 0){
            return this.getTitle().compareTo(o.getTitle());
        }else{
            return number*-1;
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
