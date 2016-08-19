package dman.hongduc.model;

import java.io.Serializable;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Tượng trưng cho 1 RSS
 *
 * @author duc
 */
public class Feed implements Serializable {

    private String title;
    private String link;
    private SortedSet<Article> articles;

    public Feed() {

    }

    /**
     * Tạo đối tượng RSS
     *
     * @param title tiêu đề của RSS
     * @param link link tới trang cung cấp RSS
     * @param articles danh sách articles
     */
    public Feed(String title, String link, SortedSet<Article> articles) {
        this.title = title;
        this.link = link;
        this.articles = articles;
    }
    
    public Feed(String title,String link){
        this(title, link, new TreeSet<>());
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

    /**
     * @return the articles
     */
    @XmlElement(name = "Article")
    @XmlElementWrapper(name = "Articles")
    public SortedSet<Article> getArticles() {
        return articles;
    }

    /**
     *
     * @param articles the articles to set
     */
    public void setArticles(SortedSet<Article> articles) {
        this.articles = articles;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.getLink().hashCode();
        hash = 97 * hash + this.getTitle().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Feed) {
            Feed feed = (Feed) obj;
            return (feed.getLink().equalsIgnoreCase(this.getLink())) && (feed.getTitle().equalsIgnoreCase(this.getTitle()));
        }
        return false;
    }

}
