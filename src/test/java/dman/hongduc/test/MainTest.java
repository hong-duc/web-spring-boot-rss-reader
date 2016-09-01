/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dman.hongduc.test;

import dman.hongduc.controllers.ArticleController;
import dman.hongduc.model.Article;
import dman.hongduc.model.Feed;
import dman.hongduc.model.FeedUtility;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author duc
 */
public class MainTest {

    private List<Feed> mainData;
    private ArticleController articleController;
    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void breakDownClass() {
    }

    @Before
    public void setUp() {
        mainData = FeedUtility.getUserFeeds("myuser", FeedUtility.DEFAULT_PATH_NAME).orElse(Collections.emptyList());
        articleController = new ArticleController();
    }

    @After
    public void breakDown() {
        FeedUtility.saveFeeds(mainData, "myuser", FeedUtility.DEFAULT_PATH_NAME);
    }

    @Test
    public void testLoad() {
        List<Feed> feeds = FeedUtility.getUserFeeds("myuser", FeedUtility.DEFAULT_PATH_NAME).orElse(Collections.emptyList());
        Assert.assertEquals("The list should not empty", feeds.isEmpty(), false);
        Assert.assertEquals("danh sach Article khong duoc empty", feeds.get(0).getArticles().isEmpty(), false);
        Assert.assertNotNull("article khong duoc null", feeds.get(0).getArticles().first().getTitle());
    }

    @Test
    public void testSave() {
        boolean result = FeedUtility.saveFeeds(mainData, "myuser", FeedUtility.DEFAULT_PATH_NAME);
        Assert.assertTrue("result phai la true", result);
    }

    @Test
    public void deleteArticlesFromSetTest() {
        String[] titles = {
            "Time Series Database Top10 Updated: Atlas, Heroic, Chronix, Hawkular and Warp 10",
            "A Positive Note to Programmers"
        };

        SortedSet<Article> origin = mainData.get(0).getArticles();
        SortedSet<Article> not_origin = articleController.deleteArticlesFromSet(origin, titles);
        Assert.assertNotEquals("orgin va not_origin khong duoc cung size", origin.size(), not_origin.size());
        Assert.assertNotSame("origin va not_origin khong duoc chieu cung 1 object", origin, not_origin);
        Assert.assertEquals("not_origin phai mat 2 article", origin.size() - 2, not_origin.size());
    }

    @Test
    public void updateReadOfArticlesByTitleTest() {
        try {
            String[] titles = {
                "Time Series Database Top10 Updated: Atlas, Heroic, Chronix, Hawkular and Warp 10",
                "A Positive Note to Programmers"
            };
            SortedSet<Article> origin = mainData.get(0).getArticles();
            long numberOfReadArticleOrigin = origin.stream().filter(a -> a.isRead()).count();
            Method method = ArticleController.class.getDeclaredMethod("updateReadOfArticlesByTitle", Collection.class, String[].class);
            method.setAccessible(true);
            SortedSet<Article> not_origin = (SortedSet<Article>) method.invoke(articleController, origin, titles);
            Assert.assertNotNull(not_origin);
            long numberOfReadArticleNotOrigin = not_origin.stream().filter(a -> a.isRead()).count();
            Assert.assertEquals("origin va not_origin phai cung size", origin.size(), not_origin.size());
            Assert.assertNotEquals("number of read should diff", numberOfReadArticleOrigin, numberOfReadArticleNotOrigin);
            Assert.assertNotSame("origin va not_origin khong duoc chieu cung 1 object", origin, not_origin);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            LOG.error("error in updateReadOfArticlesByTitleTest", ex);
        }
    }

}
