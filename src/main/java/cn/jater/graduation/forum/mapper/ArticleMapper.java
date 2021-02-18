package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Article;
import cn.jater.graduation.forum.service.ArticleService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ArticleMapper extends ArticleService {
    @Override
    @Select({"select " +
            "a.*, " +
            "aa.is_like as is_author_like, " +
            "ab.is_like as is_like, " +
            "ab.is_thumbs_up as is_thumbs_up " +
            "from article a " +
            "left join user_author_likes aa on aa.author_id = a.author_id and aa.user_id = #{user_id} " +
            "left join user_article_likes ab on ab.article_id = a._id and ab.user_id = #{user_id} " +
            "where a.topic_id = #{topic_id} " +
            " order by a.create_time limit #{start}, #{pageSize}"})
    List<Article> findArticleDetailByUserIdAndTopicId(String user_id, String topic_id, int start, int pageSize);
}