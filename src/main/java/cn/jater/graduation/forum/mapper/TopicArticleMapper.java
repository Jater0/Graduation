package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.service.TopicArticleService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicArticleMapper extends TopicArticleService {
    @Override
    @Select({"select article_id from topic_article where topic_id = #{topic_id}"})
    List<String> findArticleIdsByTopicId(String topic_id);
}