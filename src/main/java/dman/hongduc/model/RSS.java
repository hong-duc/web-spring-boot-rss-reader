package dman.hongduc.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tượng trưng cho 1 RSS
 *
 * @author duc
 */
public class RSS implements Serializable {

    private String title;
    private String link;
    private final List<Article> articles;

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

    /**
     * Nén danh sách RSS
     * @param object danh sách RSS để nén
     * @param user tên người dùng
     * @return true nếu nén thành công, ngược lại false
     */
    public static boolean serializeRSS(List<RSS> object, String user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("users/" + user + "/rss.data")))) {
            oos.writeObject(object);
            return true;
        } catch (IOException ex) {
            Logger.getLogger(RSS.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    /**
     * giải nén danh sách RSS của user
     * @param user tên người dùng
     * @return danh sách RSS nếu có, nếu không có trả về danh sách rỗng
     */
    public static List<RSS> deserializeRSS(String user){
        try(ObjectInputStream oos = new ObjectInputStream(new FileInputStream(new File("users/" + user + "/rss.data")))){
            Object obj = oos.readObject();
            if(obj instanceof List){
                List<RSS> rss = (ArrayList<RSS>) obj;
                return rss;
            }else{
                return new ArrayList<>();
            }
        }catch(IOException | ClassNotFoundException ex){
            Logger.getLogger(RSS.class.getName()).log(Level.SEVERE, null, ex);
            return new ArrayList<>();
        }
    }

}
