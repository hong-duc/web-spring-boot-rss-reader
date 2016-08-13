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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * quản lý RSS
 *
 * @author duc
 */
@Controller
@RequestMapping("/rss-app")
public class RssController {

    private List<RSS> userRSS = null;
    private String user = "myuser";

    @RequestMapping(value = "/{user}")
    public String index(@PathVariable String user, Model model) {
        if (this.user.equalsIgnoreCase(user) && !"".equals(user)) {
            if (userRSS == null) {
                userRSS = getUserRSS(user);
            }
            model.addAttribute("userRSS", userRSS);
            return "app/index";
        }else{
            return "redirect: /";
        }
    }

    @RequestMapping(value = "/them-link", method = RequestMethod.POST)
    public String themLink(@RequestParam String inputLink) {
        RSS rss = generateRSS(inputLink);
        userRSS.add(rss);
        RSS.serializeRSS(userRSS, this.user);
        return "redirect:/rss-app/" + this.user;
    }

    private List<RSS> getUserRSS(String user) {
        List<RSS> rss = RSS.deserializeRSS(user);
        return rss;
    }

    private RSS generateRSS(String url) {
        try {
            URL feedUrl = new URL(url);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build(new XmlReader(feedUrl));

            RSS rss = new RSS(feed.getTitle(), url);
            List<Article> articles = rss.getArticles();

            feed.getEntries().stream()
                    .map((entry) -> new Article(entry.getTitle(), entry.getLink()))
                    .forEach((article) -> articles.add(article));

            return rss;

        } catch (IOException | IllegalArgumentException | FeedException e) {
            return null;
        }
    }

    private boolean isUserExists(String user) {
        File file = new File("users/" + user);
        return file.exists();
    }
}
