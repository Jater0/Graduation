package cn.jater.graduation.forum.entries;

import java.io.Serializable;

public class UserFollow implements Serializable {
    private int id;
    private String _id;
    private String author_name;
    private String author_phone;
    private String author_email;
    private String gender;
    private String avatar;
    private String explain;
    private String professional;
    private int fans_count;
    private int follow_count;
    private int integral_count;
    private int article_count;
    private Boolean is_like;

    public UserFollow() {

    }

    public UserFollow(int id, String _id, String author_name, String author_phone, String author_email, String gender, String avatar, String explain, String professional, int fans_count, int follow_count, int integral_count, int article_count, Boolean is_like) {
        this.id = id;
        this._id = _id;
        this.author_name = author_name;
        this.author_phone = author_phone;
        this.author_email = author_email;
        this.gender = gender;
        this.avatar = avatar;
        this.explain = explain;
        this.professional = professional;
        this.fans_count = fans_count;
        this.follow_count = follow_count;
        this.integral_count = integral_count;
        this.article_count = article_count;
        this.is_like = is_like;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAuthor_name() {
        return author_name;
    }

    public void setAuthor_name(String author_name) {
        this.author_name = author_name;
    }

    public String getAuthor_phone() {
        return author_phone;
    }

    public void setAuthor_phone(String author_phone) {
        this.author_phone = author_phone;
    }

    public String getAuthor_email() {
        return author_email;
    }

    public void setAuthor_email(String author_email) {
        this.author_email = author_email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getExplain() {
        return explain;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }

    public int getFans_count() {
        return fans_count;
    }

    public void setFans_count(int fans_count) {
        this.fans_count = fans_count;
    }

    public int getFollow_count() {
        return follow_count;
    }

    public void setFollow_count(int follow_count) {
        this.follow_count = follow_count;
    }

    public int getIntegral_count() {
        return integral_count;
    }

    public void setIntegral_count(int integral_count) {
        this.integral_count = integral_count;
    }

    public int getArticle_count() {
        return article_count;
    }

    public void setArticle_count(int article_count) {
        this.article_count = article_count;
    }

    public Boolean getIs_like() {
        return is_like;
    }

    public void setIs_like(Boolean is_like) {
        this.is_like = is_like;
    }
}
