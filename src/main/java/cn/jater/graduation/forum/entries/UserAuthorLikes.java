package cn.jater.graduation.forum.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserAuthorLikes implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    @JsonIgnore
    private String user_id;
    private String author_id;
    private boolean is_like;

    public UserAuthorLikes() {

    }

    public UserAuthorLikes(int id, String user_id, String author_id, boolean is_like) {
        this.id = id;
        this.user_id = user_id;
        this.author_id = author_id;
        this.is_like = is_like;
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

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public boolean isIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }
}
