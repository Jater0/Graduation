package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Topic;
import cn.jater.graduation.forum.mapper.TopicMapper;
import cn.jater.graduation.forum.service.TopicService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Service
public class TopicServiceImpl implements TopicService {

    @Override
    public Topic findTopicByTopicId(String topic_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        Topic topic = mapper.findTopicByTopicId(topic_id);
        sqlSession.commit();
        sqlSession.close();
        return topic;
    }

    @Override
    public List<Topic> findTopicOwn(String id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        List<Topic> topicList = mapper.findTopicOwn(id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return topicList;
    }

    @Override
    public List<String> findCoverByTopicId(String topic_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        List<String> output = mapper.findCoverByTopicId(topic_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public int insertTopicWithSqlFuncWithoutCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        int insertSuccess = mapper.insertTopicWithSqlFuncWithoutCovers(topic_id, user_id, title, classify, create_time);
        sqlSession.commit();
        sqlSession.close();
        return insertSuccess;
    }

    @Override
    public int insertTopicWithSqlFuncWithCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time, List<String> covers) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        int insertFirst = mapper.insertTopicWithSqlFuncWithoutCovers(topic_id, user_id, title, classify, create_time);
        int insertSecond = 0;
        if (insertFirst == 1 && !covers.isEmpty()) {
            insertSecond = mapper.insertTopicCovers(covers, topic_id);
        }
        sqlSession.commit();
        sqlSession.close();
        return insertFirst + insertSecond;
    }

    @Override
    public int insertTopicWithAdmin(String id, String author_id, String author_name, String avatar, String content, String type, List<String> covers) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        int insertTopic = mapper.insertTopicWithAdminAndCovers(id, author_id, author_name, avatar, content, type);
        int insertTopicCover = 0;
        if (!covers.isEmpty() && insertTopic == 1) {
            insertTopicCover = mapper.insertTopicCovers(covers, id);
        }
        sqlSession.commit();
        sqlSession.close();
        return insertTopic + insertTopicCover;
    }

    @Override
    public int deleteTopic(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        int deleteCode = mapper.deleteTopic(id);
        sqlSession.commit();
        sqlSession.close();
        return deleteCode;
    }

    @Override
    public List<Map<String, String>> findHotListLimitIIX() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        TopicMapper mapper = sqlSession.getMapper(TopicMapper.class);
        List<Map<String, String>> output = mapper.findHotListLimitIIX();
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}