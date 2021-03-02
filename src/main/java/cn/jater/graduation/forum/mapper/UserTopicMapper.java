package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.UserTopic;
import cn.jater.graduation.forum.service.UserTopicService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserTopicMapper extends UserTopicService {
    @Override
    @Select({"select t.*, u.is_like from topic t " +
            "left join user_article_likes u " +
            "on t._id = u.article_id and u.user_id = #{user_id} " +
            "where t.classify = #{name} and t.is_delete = 0 " +
            "order by t.create_time desc " +
            "limit #{start}, #{pageSize}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
                            fetchType = FetchType.EAGER))
    })
    List<UserTopic> findTopicWithLikesByUserId(String user_id, String name, int start, int pageSize);

    @Override
    @Select({"select * from topic " +
            "where classify = #{name} and is_delete = 0 " +
            "order by create_time desc " +
            "limit #{start}, #{pageSize}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
                            fetchType = FetchType.EAGER))
    })
    List<UserTopic> findTopicStateless(String name, int start, int pageSize);

    @Override
    @Select({"select * from topic where is_delete = 0 order by browse_count desc limit #{start}, #{pageSize}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
                            fetchType = FetchType.EAGER))
    })
    List<UserTopic> findTopicWithRecommendStateless(int start, int pageSize);

    @Override
    @Select({"select t.*, u.is_like from topic t " +
            "left join user_article_likes u on t._id = u.article_id and u.user_id = #{user_id} " +
            "where exists(select 1 from user_author_likes a where t.author_id = a.author_id and a.user_id = #{user_id} and a.is_like = 1) " +
            "and t.is_delete = 0 " +
            "order by t.create_time desc limit #{start}, #{size}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "cover", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
                            fetchType = FetchType.EAGER))
    })
    List<UserTopic> findTopicWithFollowingByUserId(String user_id, int start, int size);

//    @Override
//    @Select({"select t.*, u.is_like from topic t " +
//            "left join user_article_likes u " +
//            "on t._id = u.article_id and u.user_id = #{user_id} " +
//            "where t.classify = #{name} and t.is_delete = 0 " +
//            "order by t.create_time desc limit #{start}, #{pageSize}"})
//    @Results({
//            @Result(property = "_id", column = "_id"),
//            @Result(property = "cover", column = "_id",
//                    many = @Many(select = "cn.jater.graduation.forum.mapper.TopicMapper.findCoverByTopicId",
//                            fetchType = FetchType.EAGER))
//    })
//    List<UserTopic> findTopicHotListWithFollowingByUserIdAndTopicId(String user_id, String topic, int start, int page);
}
