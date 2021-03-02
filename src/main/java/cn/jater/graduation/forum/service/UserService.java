package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.User;

import java.util.List;
import java.util.Map;


public interface UserService {
    User findUserByWeChatId(String id);
    void createUser(String id, String name, String avatar, String gender);
    List<User> findAllUser(int start, int pageSize);
    Map<String, Object> findAllUserWithCount(int start, int pageSize);
    int updateGender(String id, String gender);
    int updateExplain(String id, String explain);
    int updateProfessional(String id, String professional);
}
