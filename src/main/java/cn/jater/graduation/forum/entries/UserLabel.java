package cn.jater.graduation.forum.entries;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class UserLabel implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    @JsonIgnore
    private String user_id;
    private String label_name;
    private boolean current;

    public UserLabel() {

    }

    public UserLabel(int id, String user_id, String label_name, boolean current) {
        super();
        this.id = id;
        this.user_id = user_id;
        this.label_name = label_name;
        this.current = current;
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

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_name(String label_name) {
        this.label_name = label_name;
    }

    public boolean getCurrent() {
        return current;
    }

    public void setCurrent(boolean current) {
        this.current = current;
    }
}
