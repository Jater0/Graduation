package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Article;
import cn.jater.graduation.forum.mapper.ArticleMapper;
import cn.jater.graduation.forum.mapper.TopicMapper;
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

    @Override
    public List<Article> findArticleDetailByUserIdAndArticleId(String user_id, String article_id, int start, int pageSize) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        List<Article> output = mapper.findArticleDetailByUserIdAndArticleId(user_id, article_id, start, pageSize);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<Article> findArticleByUserLike(String user_id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        List<Article> output = mapper.findArticleByUserLike(user_id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<Article> findArticleByOwn(String user_id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        List<Article> output = mapper.findArticleByOwn(user_id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<Article> findArticleWithLikesByAuthorId(String user_id, String author_id, int start, int size) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        List<Article> output = mapper.findArticleWithLikesByAuthorId(user_id, author_id, start, size);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public int insertArticleByAdmin(String article_id, String uploader_id, String uploader_name, String uploader_avatar, String title, String classify, String content) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int insertCode = mapper.insertArticleByAdmin(article_id, uploader_id, uploader_name, uploader_avatar, title, classify, content);
        sqlSession.commit();
        sqlSession.close();
        return insertCode;
    }

    @Override
    public int insertArticleByAdmin(String article_id, String uploader_id, String uploader_name, String uploader_avatar, String title, String classify, String content, List<String> covers) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int insertCode = mapper.insertArticleByAdmin(article_id, uploader_id, uploader_name, uploader_avatar, title, classify, content);
        int insertCover = 0;
        if (!covers.isEmpty() && insertCode == 1) {
            insertCover = mapper.insertArticleCovers(covers, article_id);
        }
        sqlSession.commit();
        sqlSession.close();
        return insertCode + insertCover;
    }

    @Override
    public int answerTopic(String article_id, String topic_id, String uploader_id,
                           String uploader_name, String uploader_avatar, String title,
                           String classify, String content) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int insertCode = mapper.answerTopic(article_id, topic_id, uploader_id, uploader_name, uploader_avatar, title, classify, content);
        sqlSession.commit();
        sqlSession.close();
        return insertCode;
    }

    @Override
    public int answerTopicUser(String article_id, String topic_id, String uploader_id,
                               String title, String classify, String content) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int insertCode = mapper.answerTopicUser(article_id, topic_id, uploader_id, title, classify, content);
        sqlSession.commit();
        sqlSession.close();
        return insertCode;
    }

    @Override
    public int insertArticleByUser(String article_id, String uploader_id, String title,
                                   String classify, String content, List<String> covers) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int insertCode = mapper.insertArticleByUser(article_id, uploader_id, title, classify, content);
        int insertCover = 0;
        if (!covers.isEmpty() && insertCode != 0) {
            insertCover = mapper.insertArticleCovers(covers, article_id);
        }
        sqlSession.commit();
        sqlSession.close();
        return insertCode + insertCover;
    }

    @Override
    public int deleteArticle(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int deleteArticleCode = mapper.deleteArticle(id);
        sqlSession.commit();
        sqlSession.close();
        return deleteArticleCode;
    }

    @Override
    public int updateArticleBrowse(String article) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        ArticleMapper mapper = sqlSession.getMapper(ArticleMapper.class);
        int updateCode = mapper.updateArticleBrowse(article);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }
}
