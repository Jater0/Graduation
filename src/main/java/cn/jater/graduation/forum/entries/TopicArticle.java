package cn.jater.graduation.forum.entries;

import java.io.Serializable;

public class TopicArticle implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    private String topic_id;
    private String article_id;

    public TopicArticle() {

    }

    public TopicArticle(int id, String topic_id, String article_id) {
        this.id = id;
        this.topic_id = topic_id;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }
}
