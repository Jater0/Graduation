package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.UserFollow;
import cn.jater.graduation.forum.service.UserFollowService;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

@Mapper
public interface UserFollowMapper extends UserFollowService {
    @Override
    @Select({"select u.*, al.is_like " +
            "from user u, user_author_likes al " +
            "where u._id = al.author_id " +
            "and al.user_id = #{id} " +
            "and al.user_id != al.author_id " +
            "limit #{start}, #{size}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "article_count", column = "_id",
                    one = @One(select = "cn.jater.graduation.forum.mapper.ArticleMapper.findArticleCountByUserId",
                                fetchType = FetchType.EAGER))
    })
    List<UserFollow> findFollowingDetailByUserId(String id, int start, int size);

    @Override
    @Select({"select u.* " +
            "from user u, user_author_likes al " +
            "where u._id = al.user_id " +
            "and al.author_id = #{id} " +
            "and al.user_id != al.author_id " +
            "limit #{start}, #{size}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "article_count", column = "_id",
                    one = @One(select = "cn.jater.graduation.forum.mapper.ArticleMapper.findArticleCountByUserId",
                            fetchType = FetchType.EAGER))
    })
    List<UserFollow> findFansDetailByUserId(String id, int start, int size);

    @Override
    @Select({"select u.*, al.is_like " +
            "from user u, user_author_likes al " +
            "where u._id = #{author_id} and " +
            "al.user_id = #{user_id} and " +
            "al.author_id = #{author_id}"})
    UserFollow findOthersDetailByUserId(String user_id, String author_id);
}
