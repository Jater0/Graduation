package cn.jater.graduation.forum.entries;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Feedback implements Serializable {
    private String _id;
    private String user_id;
    private String feedback_content;
    private boolean is_check;
    private int feedback_type;
    private Timestamp create_time;
    private List<String> feedback_cover;

    public Feedback() {

    }

    public Feedback(String _id, String user_id, String feedback_content, boolean is_check, int feedback_type, List<String> feedback_cover, Timestamp create_time) {
        this._id = _id;
        this.user_id = user_id;
        this.feedback_content = feedback_content;
        this.is_check = is_check;
        this.feedback_type = feedback_type;
        this.feedback_cover = feedback_cover;
        this.create_time = create_time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }

    public boolean isIs_check() {
        return is_check;
    }

    public void setIs_check(boolean is_check) {
        this.is_check = is_check;
    }

    public int getFeedback_type() {
        return feedback_type;
    }

    public void setFeedback_type(int feedback_type) {
        this.feedback_type = feedback_type;
    }

    public List<String> getFeedback_cover() {
        return feedback_cover;
    }

    public void setFeedback_cover(List<String> feedback_cover) {
        this.feedback_cover = feedback_cover;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }
}
