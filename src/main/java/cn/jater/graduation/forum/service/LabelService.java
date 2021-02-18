package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Label;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface LabelService {
    int insertNewLabel(Label label);
    List<Label> findAll();
    List<JSONObject> findLabelAllWithStateless();
}