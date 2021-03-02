package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Topic;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public interface TopicService {
    Topic findTopicByTopicId(String topic_id);
    List<Topic> findTopicOwn(String id, int start, int size);
    List<String> findCoverByTopicId(String topic_id);
    int insertTopicWithSqlFuncWithoutCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time);
    int insertTopicWithSqlFuncWithCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time, List<String> covers);
    int insertTopicWithAdmin(String id, String author_id, String author_name, String avatar, String content, String type, List<String> covers);
    int deleteTopic(String id);
    List<Map<String, String>> findHotListLimitIIX();
}