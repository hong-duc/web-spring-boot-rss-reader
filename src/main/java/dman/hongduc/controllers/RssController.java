package dman.hongduc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import dman.hongduc.model.RSS;
import dman.hongduc.model.RssUtility;
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
    public List<RSS> getRss(@PathVariable(value = "user") String user) {
        List<RSS> rss = RssUtility.getUserRSS(user);
        if (rss == null) {
            throw new UserNotExists();
        } else {
            return rss;
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RSS themRSS(@RequestBody(required = true) String resource) {
        JSONObject obj = new JSONObject(resource);
        String link = obj.getString("link");
        String user = obj.getString("user");
        RSS rss = RssUtility.generateRss(link);
        if (rss == null) {
            throw new InvalidLink();
        }
        List<RSS> userRss = RssUtility.getUserRSS(user);
        userRss.add(rss);
        RssUtility.serializeRSS(userRss, user);
        return rss;

//        LOG.info("themRSS: link=" + link);
//	return "da nhan";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public RSS updateRSS(@RequestBody String resource) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            RSS rss = mapper.readValue(resource, RSS.class);

            rss.getArticles().addAll(0, RssUtility.generateArticles(rss.getLink()));
            return rss;
//        LOG.info("updateRSS: link=" + resource);
//        return "da nhan";
        } catch (IOException ex) {
            LOG.error("error in updateRSS:", ex);
            throw new serverError();
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "user khong ton tai")
    protected class UserNotExists extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE, reason = "invalid link")
    protected class InvalidLink extends RuntimeException {
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "có lỗi ở bên server")
    protected class serverError extends RuntimeException {
    }
}
