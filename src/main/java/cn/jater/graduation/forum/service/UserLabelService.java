package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.UserLabel;

import java.util.List;

public interface UserLabelService {
    List<UserLabel> findUserLabelByUserId(String user_id);
    int updateUserLabelForUserId(String user_id, String updates);
    int insertUserLabelWhenSignUp(String data);
}