package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Comment;

import java.sql.Timestamp;
import java.util.List;

public interface CommentService {
    List<Comment> findChildCommentByFatherId(String id);
    List<Comment> findCommentByArticleId(String id);
    int insertNewComment(String comment_id, String comment_content, Timestamp create_time, int is_reply, String father_id, String author_id, String author_name, String avatar, String to, String to_user_id);
}
