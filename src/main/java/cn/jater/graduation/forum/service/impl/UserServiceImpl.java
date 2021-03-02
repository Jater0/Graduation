package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.User;
import cn.jater.graduation.forum.mapper.UserMapper;
import cn.jater.graduation.forum.service.UserService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public User findUserByWeChatId(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.findUserByWeChatId(id);
        sqlSession.commit();
        sqlSession.close();
        return user;
    }

    @Override
    public void createUser(String id, String name, String avatar, String gender) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.createUser(id, name, avatar, gender);
        sqlSession.commit();
        sqlSession.close();
    }

    @Override
    public List<User> findAllUser(int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.findAllUser(start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return users;
    }

    @Override
    public Map<String, Object> findAllUserWithCount(int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count = mapper.countAllUser();
        List<User> users = mapper.findAllUser(start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        Map<String, Object> output = new HashMap<>();
        output.put("users", users);
        output.put("count", count);
        return output;
    }

    @Override
    public int updateGender(String id, String gender) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int updateCode = mapper.updateGender(id, gender);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }

    @Override
    public int updateExplain(String id, String explain) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int updateCode = mapper.updateExplain(id, explain);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }

    @Override
    public int updateProfessional(String id, String professional) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int updateCode = mapper.updateProfessional(id, professional);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }
}
