package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.UserArticleLikes;
import cn.jater.graduation.forum.service.UserArticleLikesService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserArticleLikesMapper extends UserArticleLikesService {
    @Override
    @Select({"select * from user_article_likes where user_id = #{user_id}"})
    List<UserArticleLikes> findUserArticleLikesThumbsByUserId(String user_id);

    @Override
    @Select({"select article_id from user_article_likes where user_id = #{user_id} and is_like = 1"})
    List<String> findUserArticleLikesByUserId(String user_id);

    @Override
    @Select({"select article_id from user_article_likes where user_id = #{user_id} and is_thumbs_up = 1"})
    List<String> findUserArticleThumbsByUserId(String user_id);

    @Override
    @Select({"call update_article_likes(#{user_id}, #{article_id})"})
    void updateArticleLikesByUserIdAndArticleId(String user_id, String article_id);

    @Override
    @Select({"select update_article_thumbs(#{user_id}, #{article_id})"})
    int updateArticleThumbsByUserIdAndArticleId(String user_id, String article_id);
}
