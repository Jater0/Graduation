package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.mapper.TopicArticleMapper;
import cn.jater.graduation.forum.service.TopicArticleService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TopicArticleServiceImpl implements TopicArticleService {

    @Override
    public List<String> findArticleIdsByTopicId(String topic_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicArticleMapper mapper = sqlSession.getMapper(TopicArticleMapper.class);
        List<String> output = mapper.findArticleIdsByTopicId(topic_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}