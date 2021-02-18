package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Comment;
import cn.jater.graduation.forum.mapper.CommentMapper;
import cn.jater.graduation.forum.service.CommentService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Override
    public List<Comment> findChildCommentByFatherId(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        List<Comment> output = mapper.findChildCommentByFatherId(id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public List<Comment> findCommentByArticleId(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        List<Comment> output = mapper.findCommentByArticleId(id);
        sqlSession.commit();
        sqlSession.close();
        return output;
    }

    @Override
    public int insertNewComment(String comment_id, String comment_content, Timestamp create_time, int is_reply, String father_id, String author_id, String author_name, String avatar, String to, String to_user_id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        CommentMapper mapper = sqlSession.getMapper(CommentMapper.class);
        int insertSuccess = mapper.insertNewComment(comment_id, comment_content, create_time, is_reply, father_id, author_id, author_name, avatar, to, to_user_id);
        sqlSession.commit();
        sqlSession.close();
        return insertSuccess;
    }
}