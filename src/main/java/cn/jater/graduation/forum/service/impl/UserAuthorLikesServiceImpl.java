package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.UserAuthorLikes;
import cn.jater.graduation.forum.mapper.UserAuthorLikesMapper;
import cn.jater.graduation.forum.service.UserAuthorLikesService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserAuthorLikesServiceImpl implements UserAuthorLikesService {

    @Override
    public List<UserAuthorLikes> findUserAuthorLikesByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserAuthorLikesMapper mapper = sqlSession.getMapper(UserAuthorLikesMapper.class);
        List<UserAuthorLikes> authorLikes = mapper.findUserAuthorLikesByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return authorLikes;
    }

    @Override
    public List<String> findUserAuthorLikesAuthorIdByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserAuthorLikesMapper mapper = sqlSession.getMapper(UserAuthorLikesMapper.class);
        List<String> output = mapper.findUserAuthorLikesAuthorIdByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public void updateAuthorLikesByUserIdAndAuthorId(String user_id, String author_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserAuthorLikesMapper mapper = sqlSession.getMapper(UserAuthorLikesMapper.class);
        mapper.updateAuthorLikesByUserIdAndAuthorId(user_id, author_id);
        sqlSession.commit();
        sqlSession.close();
    }
}