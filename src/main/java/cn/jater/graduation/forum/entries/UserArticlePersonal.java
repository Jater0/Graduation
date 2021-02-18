package cn.jater.graduation.forum.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserArticlePersonal implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    @JsonIgnore
    private String user_id;
    private String article_id;

    public UserArticlePersonal() {

    }

    public UserArticlePersonal(int id, String user_id, String article_id) {
        this.id = id;
        this.user_id = user_id;
        this.article_id = article_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getArticle_id() {
        return article_id;
    }

    public void setArticle_id(String article_id) {
        this.article_id = article_id;
    }
}
