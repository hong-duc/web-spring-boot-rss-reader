package dman.hongduc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rometools.rome.io.FeedException;
import dman.hongduc.model.Feed;
import dman.hongduc.model.FeedUtility;
import java.io.IOException;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public List<Feed> getRss(@PathVariable(value = "user") String user) {
        List<Feed> feeds = FeedUtility.getUserFeeds(user);
        if (feeds == null) {
            throw new UserNotExists();
        } else {
            return feeds;
        }
    }

    @ResponseStatus(HttpStatus.OK)
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

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void updateFeed(@RequestBody String resource) {
        try {
	    JSONObject jsObj = new JSONObject(resource);
	    Object jsFeed = jsObj.get("feed");
	    String user = jsObj.getString("user");
	     
            ObjectMapper mapper = new ObjectMapper();
            Feed feed = mapper.readValue(jsFeed.toString(), Feed.class);
            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
            for(Feed f : userFeeds){
                if(f.equals(feed)){
                    feed = f;
		    LOG.info("updateFeed: equal");
                    break;
                }
            }
            
            feed.getArticles().addAll(FeedUtility.generateArticles(feed.getLink()));
	    FeedUtility.saveFeeds(userFeeds, user);
        } catch (IOException | IllegalArgumentException ex) {
            LOG.error("updateFeed: ", ex);
            throw new NotGoodUrl();
        } catch (FeedException ex) {
            LOG.error("updateFeed: ", ex);
            throw new InvalidUrl();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteFeed(@RequestBody String resource) {
        try {
            JSONObject jsObj = new JSONObject(resource);
            Object jsFeed = jsObj.getString("feed");
            String user = jsObj.getString("user");

            ObjectMapper mapper = new ObjectMapper();
            Feed feed = mapper.readValue(jsFeed.toString(), Feed.class);

            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
            return userFeeds.remove(feed);

        } catch (IOException ex) {
            LOG.error("deleteFeed: ", ex);
            return false;
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user khong ton tai")
    protected class UserNotExists extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "không thể lấy rss từ url này hoặc không hỗ trợ")
    protected class InvalidUrl extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "có thể không phải là url")
    protected class NotGoodUrl extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "có lỗi ở bên server")
    protected class serverError extends RuntimeException {
    }
}
