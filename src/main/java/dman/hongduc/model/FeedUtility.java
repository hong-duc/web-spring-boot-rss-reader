package dman.hongduc.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import static java.util.stream.Collectors.toList;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.slf4j.LoggerFactory;

/**
 * Lớp hỗ trợ Rss
 *
 * @author duc
 */
public class FeedUtility {

    private final static org.slf4j.Logger LOG = LoggerFactory.getLogger(FeedUtility.class);
    public static final String DEFAULT_PATH_NAME = "/users/";

    /**
     * tạo ra danh sách Article từ rss url
     *
     * @param url url tới trang rss
     * @return danh sách Article
     * @throws com.rometools.rome.io.FeedException khi url không dẫn tới nơi
     * chứa xml rss
     * @throws java.io.IOException khi url chả dẫn tới đâu cả
     * @throws IllegalArgumentException khi url không phải là url
     */
    public static List<Article> generateArticles(String url) throws IllegalArgumentException, FeedException, IOException {
        SyndFeed feed = createFeed(url);

        return feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink(), getEntryDate(entry), false))
                .collect(toList());
    }

    /**
     * tạo Feed từ url
     *
     * @param url url tới trang chứa rss, thường phải có /rss hoặc .rss ở cuối
     * @return trả về feed, null nếu url không đúng
     */
    private static SyndFeed createFeed(String url) throws IllegalArgumentException, FeedException, IOException {
        SyndFeedInput feedInput = new SyndFeedInput();
        SyndFeed feed = feedInput.build(new XmlReader(new URL(url)));
        return feed;
    }

    /**
     * tạo Rss từ url
     *
     * @param url url tới trang chứa rss thường phải có /rss hoặc .rss ở cuối
     * @return RSS
     * @throws com.rometools.rome.io.FeedException khi url không dẫn tới nơi
     * chứa xml rss
     * @throws java.io.IOException khi url chả dẫn tới đâu cả
     * @throws IllegalArgumentException khi url không phải là url
     */
    public static Feed generateFeed(String url) throws IllegalArgumentException, FeedException, IOException {
        SyndFeed feed = createFeed(url);

        Feed rss = new Feed(feed.getTitle(), url);
        SortedSet<Article> articles = rss.getArticles();

        feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink(), getEntryDate(entry), false))
                .forEach((article) -> articles.add(article));
        return rss;
    }

    /**
     * lấy Rss của user này
     *
     * @param user String tên user
     * @return danh sách Rss, null nếu user không tồn tại
     */
    public static List<Feed> getUserFeeds(String user) {
        if (isUserExists(user)) {
            LOG.info("getUserRSS: user co ton tai");
            return loadFeeds(user);
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
     * Lưu danh sách Feed vào file xml
     *
     * @param feeds danh sách Feed để lưu
     * @param user tên người dùng
     * @param file_path_name tên đường dẫn tới nơi lưu file
     * @return true nếu lưu thành công, ngược lại false
     */
    public static boolean saveFeeds(List<Feed> feeds, String user, String file_path_name) {
        try {
            File file = new File(file_path_name + "/" + user + "/rss.xml");
            FeedXmlWrapper wrapper = new FeedXmlWrapper(feeds);

            JAXBContext context = JAXBContext.newInstance(FeedXmlWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(wrapper, file);
            return true;
        } catch (JAXBException ex) {
            LOG.error("saveFeeds error: ", ex);
            return false;
        }
    }

    /**
     * đọc nén danh sách RSS của user từ xml file
     *
     * @param user tên người dùng
     * @return danh sách Feed nếu có, nếu không có trả về danh sách rỗng
     */
    private static List<Feed> loadFeeds(String user) {
        try {
            File file = new File("users/" + user + "/rss.xml");

            JAXBContext context = JAXBContext.newInstance(FeedXmlWrapper.class);
            Unmarshaller u = context.createUnmarshaller();
            FeedXmlWrapper wrapper = (FeedXmlWrapper) u.unmarshal(file);
            return wrapper.getFeeds();
        } catch (JAXBException ex) {
            LOG.error("loadFeeds error: ", ex);
            return new ArrayList<>();
        }
    }

    public static LocalDate toLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate getEntryDate(SyndEntry entry) {
        return entry.getPublishedDate() == null ? toLocalDate(entry.getUpdatedDate()) : toLocalDate(entry.getPublishedDate());
    }

    public static Feed findFeedByTitle(List<Feed> feeds, String feedTitle) {
        LOG.info("findFeedByTitle: find with value = " + feedTitle);
        return feeds.stream().filter(f -> f.getTitle().equalsIgnoreCase(feedTitle))
                .limit(1)
                .findFirst()
                .get();
    }

    public static Article findArticleByTitle(Feed feed, String title) {
        return feed.getArticles().stream().filter(a -> a.getTitle().equalsIgnoreCase(title))
                .limit(1)
                .findFirst().get();
    }
}
