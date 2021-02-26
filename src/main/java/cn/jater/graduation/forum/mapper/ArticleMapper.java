package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Article;
import cn.jater.graduation.forum.service.ArticleService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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

    @Override
    @Select({"select create_article_admin(#{article_id}, #{uploader_id}, #{uploader_name}, #{uploader_avatar}, #{title}, #{classify}, #{content})"})
    int insertArticleByAdmin(String article_id, String uploader_id, String uploader_name, String uploader_avatar, String title, String classify, String content);

    @Insert({"<script> insert into cover(topic_id, cover) values " +
            "<foreach collection='covers' item='cover' separator=','>" +
            "(#{topic_id}, #{cover})\n" +
            "</foreach>" +
            "</script>"})
    int insertArticleCovers(@Param("covers") List<String> covers, String topic_id);

    @Override
    @Select({"select answer_topic_admin(#{article_id}, #{topic_id}, #{uploader_id}, " +
            "#{uploader_name}, #{uploader_avatar}, #{title}, " +
            "#{classify}, #{content})"})
    int answerTopic(String article_id, String topic_id, String uploader_id,
                    String uploader_name, String uploader_avatar, String title,
                    String classify, String content);

    @Override
    @Select({"select answer_topic_user(#{article_id}, #{topic_id}, #{uploader_id}, " +
            " #{title}, #{classify}, #{content})"})
    int answerTopicUser(String article_id, String topic_id, String uploader_id,
                        String title, String classify, String content);

    @Select({"select create_article_user(#{article_id}, #{uploader_id}, #{title}, #{classify}, #{content})"})
    int insertArticleByUser(String article_id, String uploader_id, String title, String classify, String content);

    @Override
    @Select({"select " +
            "a.*, " +
            "aa.is_like as is_author_like, " +
            "ab.is_like as is_like, " +
            "ab.is_thumbs_up as is_thumbs_up " +
            "from article a " +
            "left join user_author_likes aa on aa.author_id = a.author_id and aa.user_id = #{user_id} " +
            "left join user_article_likes ab on ab.article_id = a._id and ab.user_id = #{user_id} " +
            "where ab.is_like = 1 " +
            "order by a.create_time desc " +
            "limit #{start}, #{size}"})
    List<Article> findArticleByUserLike(String user_id, int start, int size);

    @Override
    @Select({"select * from article where author_id = #{user_id} order by create_time desc limit #{start}, #{size}"})
    List<Article> findArticleByOwn(String user_id, int start, int size);
}