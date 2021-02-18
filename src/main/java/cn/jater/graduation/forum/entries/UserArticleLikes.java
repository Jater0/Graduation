package cn.jater.graduation.forum.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserArticleLikes implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    @JsonIgnore
    private String user_id;
    private String article_id;
    private boolean is_like;
    private boolean is_thumbs_up;

    public UserArticleLikes() {

    }

    public UserArticleLikes(int id, String user_id, String article_id, boolean is_like, boolean is_thumbs_up) {
        this.id = id;
        this.user_id = user_id;
        this.article_id = article_id;
        this.is_like = is_like;
        this.is_thumbs_up = is_thumbs_up;
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

    public boolean isIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public boolean isIs_thumbs_up() {
        return is_thumbs_up;
    }

    public void setIs_thumbs_up(boolean is_thumbs_up) {
        this.is_thumbs_up = is_thumbs_up;
    }
}
