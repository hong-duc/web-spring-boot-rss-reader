package dman.hongduc.controllers;

import dman.hongduc.model.Feed;
import dman.hongduc.model.FeedUtility;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import dman.hongduc.errors.ResponseError.ServerError;
import dman.hongduc.model.Article;
import java.util.ArrayList;
import java.util.Collection;
import java.util.SortedSet;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * chạy những việc liên quan tới Article
 *
 * @author duc
 */
@RestController
@RequestMapping("/rss-app/article")
public class ArticleController {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * Rest action nhận HttpMethod Delete
     * @param feedTitle
     * @param articleTitle
     * @param user
     */
    @RequestMapping(value = "/delete/{user}/", method = RequestMethod.DELETE)
    public void restDeleteOneArticle(@RequestParam(value = "feedTitle") String feedTitle,
            @RequestParam(value = "articleTitle") String articleTitle,
            @PathVariable(value = "user") String user) {
        List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
        Optional<Feed> feed = userFeeds.stream().filter((f) -> f.getTitle().equalsIgnoreCase(feedTitle)).findFirst();
        boolean deleted = feed.get().getArticles().removeIf((a) -> a.getTitle().equalsIgnoreCase(articleTitle));
        LOG.info("deleteArticle is delete " + deleted);
        if (!deleted) {
            throw new ServerError();
        }
        FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
    }

    @RequestMapping(value = "/deletes/{user}/", method = RequestMethod.PUT)
    public boolean deleteArticles(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestBody(required = false) String resource) {
        if (resource == null) {
            LOG.info("remove all");
            return false;
        } else {
            LOG.info("remove a list articles");
            JSONArray jsonArray = new JSONArray(resource);
            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
            Feed userFeed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
            List<Article> toDeleteArticles = getArticleFromJsonArray(jsonArray, userFeed.getArticles());
            boolean deleted = userFeed.getArticles().removeAll(toDeleteArticles);
            FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
            return deleted;
        }
    }

    @RequestMapping(value = "/update/{user}/", method = RequestMethod.PUT)
    public Article updateArticle(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestParam(value = "articleTitle") String articleTitle,
            @RequestParam(value = "isRead") boolean isRead) {
        LOG.info("run updateArticle");
        List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
        Feed feed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
        Article article = FeedUtility.findArticleByTitle(feed, articleTitle);
        //article.setIsRead(isRead);
        FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
        return article;
    }

    @RequestMapping(value = "/updates/{user}/", method = RequestMethod.PUT)
    public void updateArticles(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestBody String resource) {
        LOG.info("run updateArticles");
        JSONArray jsonArray = new JSONArray(resource);
        List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
        Feed userFeed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
        SortedSet<Article> articles = updateIsReadOfArticlesByTitle(userFeed.getArticles(), jsonArray);
        //userFeed.setArticles(articles);
        FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
    }

    private List<Article> getArticleFromJsonArray(JSONArray array, SortedSet<Article> userArticles) {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            for (Article a : userArticles) {
                if (a.getTitle().equalsIgnoreCase(array.getString(i))) {
                    articles.add(a);
                    break;
                }
            }
        }
        return articles;
    }

    private SortedSet<Article> updateIsReadOfArticlesByTitle(SortedSet<Article> articles, JSONArray jsonArray) {
        if (articles.size() == jsonArray.length()) {
            articles.forEach(a -> {
                //a.setIsRead(true);
            });
        } else {
            for (Article a : articles) {
                if (jsonArray.length() == 0) {
                    break;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        if (a.getTitle().equalsIgnoreCase(jsonArray.getString(i))) {
                            //a.setIsRead(true);
                            jsonArray.remove(i);
                        }
                    }
                }
            }
        }
        return articles;
    }
    
    /**
     * Xóa một hoặc nhiều article khỏi danh sách
     * ,nếu article đó có title nằm trong danh sách
     * titleToDelete
     * 
     * @param <R> kiểu danh sách muốn trả về
     * @param setArticles một danh sách article muốn xóa khỏi
     * @param titleToDelete danh sách các title để so sánh
     * @return danh sách title sau khi đã xóa
     */
    public<R extends Collection> R deleteArticlesFromSet(Collection<Article> setArticles,String[] titleToDelete){
        Collection<Article> result = setArticles.stream().filter((Article a) -> {
            for(String t : titleToDelete){
                if(a.getTitle().equals(t))
                    return false;
            }
            return true;
        }).collect(Collectors.toList());
        return (R) result;
    }
}
