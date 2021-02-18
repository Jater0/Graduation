package cn.jater.graduation.forum.service;

import java.util.List;

public interface TopicArticleService {
    List<String> findArticleIdsByTopicId(String topic_id);
}
