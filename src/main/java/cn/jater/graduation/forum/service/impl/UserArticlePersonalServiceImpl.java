package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.mapper.UserArticlePersonalMapper;
import cn.jater.graduation.forum.service.UserArticlePersonalService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserArticlePersonalServiceImpl implements UserArticlePersonalService {
    @Override
    public List<String> findUserArticlePersonalArticleIdByUserId(String user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        UserArticlePersonalMapper mapper = sqlSession.getMapper(UserArticlePersonalMapper.class);
        List<String> output = mapper.findUserArticlePersonalArticleIdByUserId(user_id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}