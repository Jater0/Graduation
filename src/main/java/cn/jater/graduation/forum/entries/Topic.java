package cn.jater.graduation.forum.entries;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Topic implements Serializable {
    private static final long serialVersionUID=19981211L;
    private String _id;
    private String author_id;
    private String author_name;
    private String avatar;
    private String mode;
    private String title;
    private String classify;
    private Timestamp create_time;
    private int browse_count;
    private int collection_count;
    private int comments_count;
    private int thumbs_up_count;
    private boolean is_delete;
    private List<String> cover;

    public Topic() {

    }

    public Topic(String _id, String author_id, String author_name, String avatar, String mode, String title, String classify, List<String> cover, Timestamp create_time, int browse_count, int collection_count, int comments_count, int thumbs_up_count, boolean is_delete) {
        this._id = _id;
        this.author_id = author_id;
        this.author_name = author_name;
        this.avatar = avatar;
        this.mode = mode;
        this.title = title;
        this.classify = classify;
        this.cover = cover;
        this.create_time = create_time;
        this.browse_count = browse_count;
        this.collection_count = collection_count;
        this.comments_count = comments_count;
        this.thumbs_up_count = thumbs_up_count;
        this.is_delete = is_delete;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public List<String> getCover() {
        return cover;
    }

    public void setCover(List<String> cover) {
        this.cover = cover;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getBrowse_count() {
        return browse_count;
    }

    public void setBrowse_count(int browse_count) {
        this.browse_count = browse_count;
    }

    public int getCollection_count() {
        return collection_count;
    }

    public void setCollection_count(int collection_count) {
        this.collection_count = collection_count;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getThumbs_up_count() {
        return thumbs_up_count;
    }

    public void setThumbs_up_count(int thumbs_up_count) {
        this.thumbs_up_count = thumbs_up_count;
    }

    public boolean isIs_delete() {
        return is_delete;
    }

    public void setIs_delete(boolean is_delete) {
        this.is_delete = is_delete;
    }
}
