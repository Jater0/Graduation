package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Article;
import cn.jater.graduation.forum.mapper.ArticleMapper;
import cn.jater.graduation.forum.service.ArticleService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Override
    public List<Article> findArticleDetailByUserIdAndTopicId(String user_id, String topic_id, int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        List<Article> output = mapper.findArticleDetailByUserIdAndTopicId(user_id, topic_id, start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }
}
