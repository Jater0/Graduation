package cn.jater.graduation.forum.entries;

import java.io.Serializable;
import java.sql.Timestamp;

public class Article implements Serializable {
    private static final long serialVersionUID=19981211L;
    private String _id;
    private String topic_id;
    private String mode;
    private String author_id;
    private String author_name;
    private String avatar;
    private String status;
    private String classify;
    private String title;
    private String content;
    private Timestamp create_time;
    private int browse_count;
    private int collection_count;
    private int comments_count;
    private int thumbs_up_count;
    private boolean is_author_like;
    private boolean is_like;
    private boolean is_thumbs_up;

    public Article() {

    }

    public Article(String _id, String topic_id, String mode, String author_id, String author_name, String avatar, String status, String classify, String title, String content, Timestamp create_time, int browse_count, int collection_count, int comments_count, int thumbs_up_count, boolean is_author_like, boolean is_like, boolean is_thumbs_up) {
        this._id = _id;
        this.topic_id = topic_id;
        this.mode = mode;
        this.author_id = author_id;
        this.author_name = author_name;
        this.avatar = avatar;
        this.status = status;
        this.classify = classify;
        this.title = title;
        this.content = content;
        this.create_time = create_time;
        this.browse_count = browse_count;
        this.collection_count = collection_count;
        this.comments_count = comments_count;
        this.thumbs_up_count = thumbs_up_count;
        this.is_author_like = is_author_like;
        this.is_like = is_like;
        this.is_thumbs_up = is_thumbs_up;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public boolean getIs_author_like() {
        return is_author_like;
    }

    public void setIs_author_like(boolean is_author_like) {
        this.is_author_like = is_author_like;
    }

    public boolean getIs_like() {
        return is_like;
    }

    public void setIs_like(boolean is_like) {
        this.is_like = is_like;
    }

    public boolean getIs_thumbs_up() {
        return is_thumbs_up;
    }

    public void setIs_thumbs_up(boolean is_thumbs_up) {
        this.is_thumbs_up = is_thumbs_up;
    }
}
