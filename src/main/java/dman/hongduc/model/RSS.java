package dman.hongduc.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Tượng trưng cho 1 RSS
 *
 * @author duc
 */
public class RSS implements Serializable {

    private String title;
    private String link;
    private final List<Article> articles;

    public RSS() {
        this.articles = new ArrayList<>();
    }

    /**
     * Tạo đối tượng RSS
     *
     * @param title tiêu đề của RSS
     * @param link link tới trang cung cấp RSS
     */
    public RSS(String title, String link) {
        this.title = title;
        this.link = link;
        articles = new ArrayList<>();
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
    public List<Article> getArticles() {
        return articles;
    }

}
