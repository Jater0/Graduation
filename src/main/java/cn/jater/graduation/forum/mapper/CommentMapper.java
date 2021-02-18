package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Comment;
import cn.jater.graduation.forum.service.CommentService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface CommentMapper extends CommentService {
    @Override
    @Select({"select * from comment where is_reply = 1 and father_id = #{id} order by create_time desc"})
    List<Comment> findChildCommentByFatherId(String id);

    @Override
    @Select({"select * from comment where is_reply = 0 and father_id = #{id} order by create_time desc"})
    @Results({
            @Result(property = "comment_id", column = "comment_id"),
            @Result(property = "reply", column = "comment_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.CommentMapper.findChildCommentByFatherId",
                                fetchType = FetchType.EAGER))
    })
    List<Comment> findCommentByArticleId(String id);

    @Override
    @Insert({"insert into " +
            "comment(comment_id, comment_content, create_time, " +
            "is_reply, father_id, author_id, " +
            "author_name, avatar, tos, to_user_id) " +
            "values (#{comment_id}, #{comment_content}, #{create_time}, #{is_reply}, #{father_id}, #{author_id}, #{author_name}, #{avatar}, #{to}, #{to_user_id})"})
    int insertNewComment(String comment_id, String comment_content, Timestamp create_time, int is_reply, String father_id, String author_id, String author_name, String avatar, String to, String to_user_id);
}