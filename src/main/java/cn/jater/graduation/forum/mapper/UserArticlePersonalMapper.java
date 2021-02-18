package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.service.UserArticlePersonalService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserArticlePersonalMapper extends UserArticlePersonalService {
    @Override
    @Select({"select article_id from user_article_personal where user_id = #{user_id}"})
    List<String> findUserArticlePersonalArticleIdByUserId(String user_id);

}