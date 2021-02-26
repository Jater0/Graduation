package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.UserTopic;
import cn.jater.graduation.forum.mapper.UserTopicMapper;
import cn.jater.graduation.forum.service.UserTopicService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTopicServiceImpl implements UserTopicService {

    @Override
    public List<UserTopic> findTopicWithLikesByUserId(String user_id, String name, int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserTopicMapper mapper = sqlSession.getMapper(UserTopicMapper.class);
        List<UserTopic> output = mapper.findTopicWithLikesByUserId(user_id, name, start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<UserTopic> findTopicStateless(String name, int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserTopicMapper mapper = sqlSession.getMapper(UserTopicMapper.class);
        List<UserTopic> output = mapper.findTopicStateless(name, start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<UserTopic> findTopicWithFollowingByUserId(String user_id, int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserTopicMapper mapper = sqlSession.getMapper(UserTopicMapper.class);
        List<UserTopic> output = mapper.findTopicWithFollowingByUserId(user_id, start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<UserTopic> findTopicWithRecommendStateless(int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserTopicMapper mapper = sqlSession.getMapper(UserTopicMapper.class);
        List<UserTopic> output = mapper.findTopicWithRecommendStateless(start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}