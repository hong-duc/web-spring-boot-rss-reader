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
import java.util.Collections;
import java.util.List;
import java.util.SortedSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author duc
 */
public class MainTest {

    private List<Feed> mainData;
    private ArticleController articleController;

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

}
