/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dman.hongduc.model;

import java.time.LocalDate;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author duc
 */
@XmlRootElement(name = "Feeds")
public class FeedXmlWrapper extends XmlAdapter<String, LocalDate>{

    private List<Feed> feeds;

    public FeedXmlWrapper() {
    }

    public FeedXmlWrapper(List<Feed> feeds) {
        this.feeds = feeds;
    }

    /**
     * @return the feeds
     */
    @XmlElement(name = "Feed")
    public List<Feed> getFeeds() {
        return feeds;
    }

    /**
     * @param feeds the feeds to set
     */
    public void setFeeds(List<Feed> feeds) {
        this.feeds = feeds;
    }

    @Override
    public LocalDate unmarshal(String v) throws Exception {
        return LocalDate.parse(v);
    }

    @Override
    public String marshal(LocalDate v) throws Exception {
        return v.toString();
    }
}
