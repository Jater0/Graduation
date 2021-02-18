package cn.jater.graduation.forum.entries;

import java.io.Serializable;
import java.sql.Timestamp;

public class Label implements Serializable {
    private static final long serialVersionUID=19981211L;
    private String _id;
    private String label_name;
    private Timestamp create_time;

    public Label() {

    }

    public Label(String _id, String label_name, Timestamp create_time) {
        super();
        this._id = _id;
        this.label_name = label_name;
        this.create_time = create_time;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getLabel_name() {
        return label_name;
    }

    public void setLabel_Name(String label_name) {
        this.label_name = label_name;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public String toStringWithCurrentBoolean(boolean current) {
        return "{" +
                "\"id\":" + _id + "," +
                "\"label_name\":\"" + label_name + "\"," +
                "\"current\":" + current + "}";
    }
}
