package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.UserFollow;
import cn.jater.graduation.forum.mapper.UserFollowMapper;
import cn.jater.graduation.forum.service.UserFollowService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFollowServiceImpl implements UserFollowService {
    @Override
    public List<UserFollow> findFollowingDetailByUserId(String id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserFollowMapper mapper = sqlSession.getMapper(UserFollowMapper.class);
        List<UserFollow> userFollows = mapper.findFollowingDetailByUserId(id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return userFollows;
    }

    @Override
    public List<UserFollow> findFansDetailByUserId(String id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserFollowMapper mapper = sqlSession.getMapper(UserFollowMapper.class);
        List<UserFollow> userFans = mapper.findFansDetailByUserId(id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return userFans;
    }

    @Override
    public UserFollow findOthersDetailByUserId(String user_id, String author_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserFollowMapper mapper = sqlSession.getMapper(UserFollowMapper.class);
        UserFollow userFollow = mapper.findOthersDetailByUserId(user_id, author_id);
        sqlSession.commit();
        sqlSession.close();
        return userFollow;
    }
}
