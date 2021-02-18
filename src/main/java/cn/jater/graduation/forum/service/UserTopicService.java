package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.UserTopic;

import java.util.List;

public interface UserTopicService {
    List<UserTopic> findTopicWithLikesByUserId(String user_id, String name, int start, int pageSize);
    List<UserTopic> findTopicStateless(String name, int start, int pageSize);
    List<UserTopic> findTopicWithFollowingByUserId(String user_id, int start, int size);
}