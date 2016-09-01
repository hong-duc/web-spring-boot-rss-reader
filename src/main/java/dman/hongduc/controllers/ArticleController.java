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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
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


    @RequestMapping(value = "/deletes/{user}/", method = RequestMethod.PUT)
    public boolean deleteArticles(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestBody(required = false) String resource) {
//        if (resource == null) {
//            LOG.info("remove all");
//            return false;
//        } else {
//            LOG.info("remove a list articles");
//            JSONArray jsonArray = new JSONArray(resource);
//            List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
//            Feed userFeed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
//            List<Article> toDeleteArticles = getArticleFromJsonArray(jsonArray, userFeed.getArticles());
//            boolean deleted = userFeed.getArticles().removeAll(toDeleteArticles);
//            FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
//            return deleted;
//        }
        return false;
    }

    @RequestMapping(value = "/update/{user}/", method = RequestMethod.PUT)
    public Article updateArticle(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestParam(value = "articleTitle") String articleTitle,
            @RequestParam(value = "isRead") boolean isRead) {
//        LOG.info("run updateArticle");
//        List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
//        Feed feed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
//        Article article = FeedUtility.findArticleByTitle(feed, articleTitle);
//        //article.setIsRead(isRead);
//        FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
//        return article;
        return null;
    }

    @RequestMapping(value = "/updates/{user}/", method = RequestMethod.PUT)
    public void updateArticles(@PathVariable(value = "user") String user,
            @RequestParam(value = "feedTitle") String feedTitle,
            @RequestBody String resource) {
//        LOG.info("run updateArticles");
//        JSONArray jsonArray = new JSONArray(resource);
//        List<Feed> userFeeds = FeedUtility.getUserFeeds(user);
//        Feed userFeed = FeedUtility.findFeedByTitle(userFeeds, feedTitle);
//        SortedSet<Article> articles = updateIsReadOfArticlesByTitle(userFeed.getArticles(), jsonArray);
//        //userFeed.setArticles(articles);
//        FeedUtility.saveFeeds(userFeeds, user,FeedUtility.DEFAULT_PATH_NAME);
    }


    /**
     * update những article nào có title trong danh sách titleToUpdate
     * 
     * @param articles danh sách articles muốn update
     * @param titleToUpdate danh sách title để so sanh với article
     * @return một SortedSet<Article> đã update
     */
    private SortedSet<Article> updateReadOfArticlesByTitle(Collection<Article> articles, String... titleToUpdate) {
        SortedSet<Article> result = articles.stream().map((Article a) -> {
            for(String title : titleToUpdate){
                if(a.getTitle().equals(title)){
                    return new Article(a.getTitle(), a.getLink(), a.getPublishDate(), true);
                }
            }
            return a;
        }).collect(Collectors.toCollection(TreeSet::new));
        return result;
    }

    /**
     * Xóa một hoặc nhiều article khỏi danh sách ,nếu article đó có title nằm
     * trong danh sách titleToDelete
     *
     * @param setArticles một danh sách article muốn xóa khỏi
     * @param titleToDelete danh sách các title để so sánh
     * @return danh sách title sau khi đã xóa
     */
    public SortedSet<Article> deleteArticlesFromSet(Collection<Article> setArticles, String... titleToDelete) {
        SortedSet<Article> result = setArticles.stream().filter((Article a) -> {
            for (String t : titleToDelete) {
                if (a.getTitle().equals(t)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toCollection(TreeSet::new));
        return result;
    }
}
