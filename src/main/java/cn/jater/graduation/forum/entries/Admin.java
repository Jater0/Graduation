package cn.jater.graduation.forum.entries;

import java.io.Serializable;
import java.sql.Timestamp;

public class Admin implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    private String _id;
    private Timestamp create_time;
    private String admin_name;
    private String admin_phone;
    private String admin_email;
    private String password;
    private String gender;
    private String avatar;
    private String explain;
    private int professional;

    public Admin() {

    }

    public Admin(int id, String _id, Timestamp create_time, String admin_name, String admin_phone, String admin_email, String password, String gender, String avatar, String explain, int professional) {
        this.id = id;
        this._id = _id;
        this.create_time = create_time;
        this.admin_name = admin_name;
        this.admin_phone = admin_phone;
        this.admin_email = admin_email;
        this.password = password;
        this.gender = gender;
        this.avatar = avatar;
        this.explain = explain;
        this.professional = professional;
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

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String getAdmin_name() {
        return admin_name;
    }

    public void setAdmin_name(String admin_name) {
        this.admin_name = admin_name;
    }

    public String getAdmin_phone() {
        return admin_phone;
    }

    public void setAdmin_phone(String admin_phone) {
        this.admin_phone = admin_phone;
    }

    public String getAdmin_email() {
        return admin_email;
    }

    public void setAdmin_email(String admin_email) {
        this.admin_email = admin_email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public int getProfessional() {
        return professional;
    }

    public void setProfessional(int professional) {
        this.professional = professional;
    }
}
