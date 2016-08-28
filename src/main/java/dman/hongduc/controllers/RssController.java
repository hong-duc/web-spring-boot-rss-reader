package dman.hongduc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.io.FeedException;
import dman.hongduc.errors.ResponseError;
import dman.hongduc.errors.ResponseError.InvalidUrl;
import dman.hongduc.errors.ResponseError.NotGoodUrl;
import dman.hongduc.model.Feed;
import dman.hongduc.model.FeedUtility;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/{user}", method = RequestMethod.GET)
    public List<Feed> getRss(@PathVariable(value = "user") String user) {
        List<Feed> feeds = FeedUtility.getUserFeeds(user);
        if (feeds == null) {
            throw new ResponseError.UserNotExists();
        } else {
            return feeds;
        }
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Feed themFeed(@RequestBody(required = true) String resource) {
        try {
            JSONObject obj = new JSONObject(resource);
            String link = obj.getString("link");
            String user = obj.getString("user");
            Feed feed = FeedUtility.generateFeed(link);
            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
            userFeeds.add(feed);
            FeedUtility.saveFeeds(userFeeds, user);
            return feed;
        } catch (IllegalArgumentException | IOException ex) {
            LOG.error("themFeed: ", ex);
            throw new NotGoodUrl();
        } catch (FeedException ex) {
            LOG.error("themFeed: ", ex);
            throw new InvalidUrl();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public Feed updateFeed(@RequestBody String resource) {
        try {
            JSONObject jsObj = new JSONObject(resource);
            Object jsFeed = jsObj.get("feed");
            String user = jsObj.getString("user");

            ObjectMapper mapper = new ObjectMapper();
            Feed feed = mapper.readValue(jsFeed.toString(), Feed.class);
            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
            for (Feed f : userFeeds) {
                if (f.equals(feed)) {
                    feed = f;
                    LOG.info("updateFeed: equal");
                    break;
                }
            }

            feed.getArticles().addAll(FeedUtility.generateArticles(feed.getLink()));
            FeedUtility.saveFeeds(userFeeds, user);
            LOG.info("feed updated");
            return feed;
        } catch (IOException | IllegalArgumentException ex) {
            LOG.error("updateFeed: ", ex);
            throw new NotGoodUrl();
        } catch (FeedException ex) {
            LOG.error("updateFeed: ", ex);
            throw new InvalidUrl();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public boolean deleteFeed(@RequestParam(value = "link") String link, @RequestParam(value = "user") String user) {
        try {
            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);

            boolean deleted = userFeeds.removeIf(feed -> feed.getLink().equalsIgnoreCase(link));
            FeedUtility.saveFeeds(userFeeds, user);
            LOG.info("deleted= " + deleted);
            return deleted;
        } catch (RuntimeException ex) {
            LOG.error("deleteFeed error: ", ex);
            throw new ResponseError.ServerError();
        }

    }
}
