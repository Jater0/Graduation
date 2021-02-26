package cn.jater.graduation.forum.entries;

import java.io.Serializable;

public class AdminRole implements Serializable {
    private static final long serialVersionUID=19981211L;
    private int id;
    private int role_id;
    private String action_default;

    public AdminRole(int id, int role_id, String action_default) {
        this.id = id;
        this.role_id = role_id;
        this.action_default = action_default;
    }

    public AdminRole() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getAction_default() {
        return action_default;
    }

    public void setAction_default(String action_default) {
        this.action_default = action_default;
    }
}
