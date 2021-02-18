package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.UserArticleLikes;
import cn.jater.graduation.forum.mapper.UserArticleLikesMapper;
import cn.jater.graduation.forum.service.UserArticleLikesService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserArticleLikesServiceImpl implements UserArticleLikesService {

    @Override
    public List<UserArticleLikes> findUserArticleLikesThumbsByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticleLikesMapper mapper = sqlSession.getMapper(UserArticleLikesMapper.class);
        List<UserArticleLikes> articleLikes = mapper.findUserArticleLikesThumbsByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return articleLikes;
    }

    @Override
    public List<String> findUserArticleLikesByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticleLikesMapper mapper = sqlSession.getMapper(UserArticleLikesMapper.class);
        List<String> output = mapper.findUserArticleLikesByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<String> findUserArticleThumbsByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticleLikesMapper mapper = sqlSession.getMapper(UserArticleLikesMapper.class);
        List<String> output = mapper.findUserArticleThumbsByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public void updateArticleLikesByUserIdAndArticleId(String user_id, String article_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticleLikesMapper mapper = sqlSession.getMapper(UserArticleLikesMapper.class);
        mapper.updateArticleLikesByUserIdAndArticleId(user_id, article_id);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public int updateArticleThumbsByUserIdAndArticleId(String user_id, String article_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticleLikesMapper mapper = sqlSession.getMapper(UserArticleLikesMapper.class);
        int output = mapper.updateArticleThumbsByUserIdAndArticleId(user_id, article_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}