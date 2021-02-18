package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.User;
import cn.jater.graduation.forum.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

@Mapper
public interface UserMapper extends UserService {
    @Override
    @Select({"select * from user where _id = #{id}"})
    @Results({
            @Result(property = "_id", column = "_id"),
            @Result(property = "userLabels", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.UserLabelMapper.findUserLabelByUserId",
                                fetchType = FetchType.EAGER)
            ),
            @Result(property = "authorLikes", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.UserAuthorLikesMapper.findUserAuthorLikesAuthorIdByUserId",
                                fetchType = FetchType.EAGER)
            ),
            @Result(property = "articleLikes", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.UserArticleLikesMapper.findUserArticleLikesByUserId",
                                fetchType = FetchType.EAGER)
            ),
            @Result(property = "articleThumbs", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.UserArticleLikesMapper.findUserArticleThumbsByUserId",
                                fetchType = FetchType.EAGER)
            ),
            @Result(property = "articlePersonals", column = "_id",
                    many = @Many(select = "cn.jater.graduation.forum.mapper.UserArticlePersonalMapper.findUserArticlePersonalArticleIdByUserId",
                                fetchType = FetchType.EAGER)
            )
    })
    User findUserByWeChatId(String id);

    @Override
    @Select({"call create_user(#{id}, #{name}, #{gender}, #{avatar})"})
    void createUser(String id, String name, String avatar, String gender);
}
