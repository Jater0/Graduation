package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Feedback;
import cn.jater.graduation.forum.mapper.FeedbackMapper;
import cn.jater.graduation.forum.service.FeedbackService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Override
    public List<Feedback> findFeedbackAll() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        List<Feedback> feedbackAll = mapper.findFeedbackAll();
        sqlSession.commit();
        sqlSession.close();
        return feedbackAll;
    }

    @Override
    public List<String> findFeedbackCoverByFeedbackId(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        List<String> covers = mapper.findFeedbackCoverByFeedbackId(id);
        sqlSession.commit();
        sqlSession.close();
        return covers;
    }

    @Override
    public int insertFeedback(String id, String user_id, String content, int feedback_type, Timestamp create_time, List<String> covers) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        int first = mapper.insertFeedbackOne(id, user_id, content, feedback_type, create_time);
        int second = 0;
        if (!covers.isEmpty()) {
            second = mapper.insertFeedbackCovers(covers, id);
        }
        sqlSession.commit();
        sqlSession.close();
        return first + second;
    }

    @Override
    public List<Feedback> findFeedbackByType(int type, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        List<Feedback> feedback = mapper.findFeedbackByType(type, start, size);
        sqlSession.commit();
        sqlSession.close();
        return feedback;
    }

    @Override
    public List<Feedback> findFeedbackIsCheck(int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        List<Feedback> feedbackList = mapper.findFeedbackIsCheck(start, size);
        sqlSession.commit();
        sqlSession.close();
        return feedbackList;
    }

    @Override
    public int updateFeedbackState(String id, int work) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        FeedbackMapper mapper = sqlSession.getMapper(FeedbackMapper.class);
        int updateCode = mapper.updateFeedbackState(id, work);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }
}
