package dman.hongduc.controllers;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import dman.hongduc.model.Article;
import dman.hongduc.model.RSS;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import static java.util.stream.Collectors.toList;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * quản lý RSS
 *
 * @author duc
 */
@RestController
@RequestMapping("/rss-app")
public class RssController {

    private final org.slf4j.Logger LOG = LoggerFactory.getLogger(this.getClass());

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    public List<RSS> getRss(@PathVariable(value = "user") String user) {
        List<RSS> rss = this.getUserRSS(user);
        if (rss == null) {
            throw new UserNotExists();
        } else {
            return rss;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String themRSS(@RequestBody(required = true) String link) {
//        RSS rss = generateRss(inputLink);
//        if (rss == null) {
//            throw new InvalidLink();
//        }
//        List<RSS> userRss = this.getUserRSS(user);
//        userRss.add(rss);
//        RSS.serializeRSS(userRss, user);
//        return rss;

        LOG.info("themRSS: link=" + link);
	return "da nhan";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateRSS(@RequestBody String link) {
//        rss.getArticles().addAll(0, this.generateArticles(rss.getLink()));
//        return rss;
        LOG.info("updateRSS: link=" + link);
	return "da nhan";
    }

    private List<Article> generateArticles(String url) {
        SyndFeed feed = this.createFeed(url);

        return feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink()))
                .collect(toList());
    }

    private List<RSS> getUserRSS(String user) {
        if (this.isUserExists(user)) {
            LOG.info("getUserRSS: user co ton tai");
            return RSS.deserializeRSS(user);
        } else {
            LOG.error("getUserRSS: user khong ton tai");
            return null;
        }
    }

    private RSS generateRss(String url) {

        SyndFeed feed = createFeed(url);

        RSS rss = new RSS(feed.getTitle(), url);
        List<Article> articles = rss.getArticles();

        feed.getEntries().stream()
                .map((entry) -> new Article(entry.getTitle(), entry.getLink()))
                .forEach((article) -> articles.add(article));

        return rss;

    }

    private SyndFeed createFeed(String url) {
        try {
            SyndFeedInput feedInput = new SyndFeedInput();
            SyndFeed feed = feedInput.build(new XmlReader(new URL(url)));
            return feed;
        } catch (IllegalArgumentException | FeedException | IOException ex) {
            LOG.error("an error in createFeed:", ex);
            return null;
        }
    }

    private boolean isUserExists(String user) {
        File file = new File("users/" + user);
        return file.exists();
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user khong ton tai")
    protected class UserNotExists extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "invalid link")
    protected class InvalidLink extends RuntimeException {
    }
}
