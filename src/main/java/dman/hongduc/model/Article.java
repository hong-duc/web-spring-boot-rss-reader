package dman.hongduc.model;

import java.io.Serializable;

/**
 * Lớp thể hiện 1 RSS feed
 * @author duc
 */
public class Article implements Serializable{
    private String title;
    private String link;

    public Article() {
    }

    public Article(String title, String link) {
        this.title = title;
        this.link = link;
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
    
    
}
