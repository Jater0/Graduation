package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.User;

import java.util.List;


public interface UserService {
    User findUserByWeChatId(String id);
    void createUser(String id, String name, String avatar, String gender);
    List<User> findAllUser(int start, int pageSize);
}
