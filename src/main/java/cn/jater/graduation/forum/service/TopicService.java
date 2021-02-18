package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Topic;

import java.sql.Timestamp;
import java.util.List;

public interface TopicService {
    Topic findTopicByTopicId(String topic_id);
    List<String> findCoverByTopicId(String topic_id);
    int insertTopicWithSqlFuncWithoutCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time);
    int insertTopicWithSqlFuncWithCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time, List<String> covers);
}
