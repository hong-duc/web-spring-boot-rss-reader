package dman.hongduc.model;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.slf4j.LoggerFactory;

/**
 * Lớp hỗ trợ Rss
 *
 * @author duc
 */
public class RssUtility {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(RssUtility.class);

    /**
     * tạo ra danh sách Article từ rss url
     *
     * @param url url tới trang rss, thường phải có /rss hoặc .rss ở cuối
     * @return danh sách Article
     */
    public static List<Article> generateArticles(String url) {
        SyndFeed feed = createFeed(url);

        return feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink()))
                .collect(toList());
    }

    /**
     * tạo Feed từ url
     *
     * @param url url tới trang chứa rss, thường phải có /rss hoặc .rss ở cuối
     * @return trả về feed, null nếu url không đúng
     */
    private static SyndFeed createFeed(String url) {
        try {
            SyndFeedInput feedInput = new SyndFeedInput();
            SyndFeed feed = feedInput.build(new XmlReader(new URL(url)));
            return feed;
        } catch (IllegalArgumentException | FeedException | IOException ex) {
            LOG.error("an error in createFeed:", ex);
            return null;
        }
    }

    /**
     * tạo Rss từ url
     *
     * @param url url tới trang chứa rss thường phải có /rss hoặc .rss ở cuối
     * @return RSS
     */
    public static RSS generateRss(String url) {

        SyndFeed feed = createFeed(url);

        RSS rss = new RSS(feed.getTitle(), url);
        List<Article> articles = rss.getArticles();

        feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink()))
                .forEach((article) -> articles.add(article));

        return rss;

    }

    /**
     * lấy Rss của user này
     *
     * @param user String tên user
     * @return danh sách Rss, null nếu user không tồn tại
     */
    public static List<RSS> getUserRSS(String user) {
        if (isUserExists(user)) {
            LOG.info("getUserRSS: user co ton tai");
            return deserializeRSS(user);
        } else {
            LOG.error("getUserRSS: user khong ton tai");
            return null;
        }
    }

    /**
     * kiểm tra user có tồn tại trong thư mục users không
     *
     * @param user tên user để kiểm tra
     * @return true nếu tồn tại,false nếu không tồn tại
     */
    private static boolean isUserExists(String user) {
        File file = new File("users/" + user);
        return file.exists();
    }

    /**
     * Nén danh sách RSS
     *
     * @param rss danh sách RSS để nén
     * @param user tên người dùng
     * @return true nếu nén thành công, ngược lại false
     */
    public static boolean serializeRSS(List<RSS> rss, String user) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("users/" + user + "/rss.data")))) {
            oos.writeObject(rss);
            return true;
        } catch (IOException ex) {
            LOG.error("error in seralizeRSS:", ex);
            return false;
        }
    }

    /**
     * giải nén danh sách RSS của user
     *
     * @param user tên người dùng
     * @return danh sách RSS nếu có, nếu không có trả về danh sách rỗng
     */
    public static List<RSS> deserializeRSS(String user) {
        try (ObjectInputStream oos = new ObjectInputStream(new FileInputStream(new File("users/" + user + "/rss.data")))) {
            Object obj = oos.readObject();
            if (obj instanceof List) {
                List<RSS> rss = (ArrayList<RSS>) obj;
                return rss;
            } else {
                return new ArrayList<>();
            }
        } catch (IOException | ClassNotFoundException ex) {
            LOG.error("error in deserializeRSS:", ex);
            return new ArrayList<>();
        }
    }
}
