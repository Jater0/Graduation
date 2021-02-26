package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Feedback;
import cn.jater.graduation.forum.service.FeedbackService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface FeedbackMapper extends FeedbackService {
    @Override
    @Select({"select * from feedback"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "feedback_cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.FeedbackMapper.findFeedbackCoverByFeedbackId",
                                fetchType = FetchType.EAGER))
    })
    List<Feedback> findFeedbackAll();

    @Override
    @Select({"select feedback_cover from f_cover where feedback_id = #{id}"})
    List<String> findFeedbackCoverByFeedbackId(String id);

    @Insert({"insert into feedback(_id, user_id, feedback_content, feedback_type, create_time) values (#{id}, #{user_id}, #{content}, #{feedback_type}, #{create_time});"})
    int insertFeedbackOne(String id, String user_id, String content, int feedback_type, Timestamp create_time);

    @Insert({"<script> insert into f_cover(feedback_id, feedback_cover) values " +
            "<foreach collection='covers' item='cover' separator=','>" +
            " (#{feedback_id}, #{cover})\n" +
            "</foreach>" +
            "</script>"})
    int insertFeedbackCovers(@Param("covers") List<String> covers,@Param("feedback_id") String feedback_id);

    @Override
    @Select({"select * from feedback where feedback_type = #{type} and is_check = 0 limit #{start}, #{size}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "feedback_cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.FeedbackMapper.findFeedbackCoverByFeedbackId",
                            fetchType = FetchType.EAGER))
    })
    List<Feedback> findFeedbackByType(int type, int start, int size);

    @Override
    @Select({"select * from feedback where is_check = 1 limit #{start}, #{size}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "feedback_cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.FeedbackMapper.findFeedbackCoverByFeedbackId",
                            fetchType = FetchType.EAGER))
    })
    List<Feedback> findFeedbackIsCheck(int start, int size);

    @Override
    @Select({"select update_feedback(#{id}, #{work})"})
    int updateFeedbackState(String id, int work);
}