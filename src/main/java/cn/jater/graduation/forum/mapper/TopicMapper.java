package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Topic;
import cn.jater.graduation.forum.service.TopicService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TopicMapper extends TopicService {
    @Override
    @Select({"select * from topic where _id = #{topic_id}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
                                fetchType = FetchType.EAGER))
    })
    Topic findTopicByTopicId(String topic_id);

    @Override
    @Select({"select cover from cover where topic_id = #{topic_id}"})
    List<String> findCoverByTopicId(String topic_id);

    @Override
    @Select({"select create_topic(#{topic_id}, #{user_id}, #{title}, #{classify}, #{create_time})"})
    int insertTopicWithSqlFuncWithoutCovers(String topic_id, String user_id, String title, String classify, Timestamp create_time);

    @Insert({"<script> insert into cover(topic_id, cover) values " +
            "<foreach collection='covers' item='cover' separator=','>" +
            "(#{topic_id}, #{cover})\n" +
            "</foreach>" +
            "</script>"})
    int insertTopicCovers(@Param("covers") List<String> covers, String topic_id);


    @Insert({"insert into topic" +
            "(_id, author_id, author_name, avatar, `mode`, title, classify, create_time) " +
            "values" +
            "(#{id}, #{author_id}, #{author_name}, #{avatar}, \"topic\", #{content}, #{type}, NOW())"})
    int insertTopicWithAdminAndCovers(String id, String author_id, String author_name, String avatar, String content, String type);

    @Override
    @Select({"select * from topic where author_id = #{id} order by create_time desc limit #{start}, #{size}"})
    List<Topic> findTopicOwn(String id, int start, int size);
}