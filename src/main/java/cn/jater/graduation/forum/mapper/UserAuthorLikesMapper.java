package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.UserAuthorLikes;
import cn.jater.graduation.forum.service.UserAuthorLikesService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserAuthorLikesMapper extends UserAuthorLikesService {
    @Override
    @Select({"select * from user_author_likes where user_id = #{user_id}"})
    List<UserAuthorLikes> findUserAuthorLikesByUserId(String user_id);

    @Override
    @Select({"select author_id from user_author_likes where user_id = #{user_id} and is_like = 1"})
    List<String> findUserAuthorLikesAuthorIdByUserId(String user_id);

    @Override
    @Select({"call update_author_likes(#{user_id}, #{author_id})"})
    void updateAuthorLikesByUserIdAndAuthorId(String user_id, String author_id);
}