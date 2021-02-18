package cn.jater.graduation.forum.utils.data;

import cn.jater.graduation.forum.entries.Label;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DataHandler {
    public List<JSONObject> dataLabelPlusCurrentStateless(List<Label> labels) {
        List<JSONObject> output = new ArrayList<>();
        for (Label label: labels) {
            output.add(JSON.parseObject(label.toStringWithCurrentBoolean(true)));
        }
        return output;
    }
}
