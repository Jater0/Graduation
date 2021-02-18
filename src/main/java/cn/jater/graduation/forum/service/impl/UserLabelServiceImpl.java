package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.UserLabel;
import cn.jater.graduation.forum.mapper.UserLabelMapper;
import cn.jater.graduation.forum.service.UserLabelService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserLabelServiceImpl implements UserLabelService {
    @Override
    public List<UserLabel> findUserLabelByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserLabelMapper mapper = sqlSession.getMapper(UserLabelMapper.class);
        List<UserLabel> output = mapper.findUserLabelByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public int updateUserLabelForUserId(String user_id, String updates) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserLabelMapper mapper = sqlSession.getMapper(UserLabelMapper.class);
        int updateAction = mapper.updateUserLabelForUserId(user_id, updates);
        sqlSession.commit();
        sqlSession.close();
        return updateAction;
    }

    @Override
    public int insertUserLabelWhenSignUp(String data) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserLabelMapper mapper = sqlSession.getMapper(UserLabelMapper.class);
        int insertAction = mapper.insertUserLabelWhenSignUp(data);
        sqlSession.commit();
        sqlSession.close();
        return insertAction;
    }
}