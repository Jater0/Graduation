package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticleDetailByUserIdAndTopicId(String user_id, String topic_id, int start, int pageSize);
    List<Article> findArticleDetailByUserIdAndArticleId(String user_id, String article_id, int start, int pageSize);
    List<Article> findArticleByUserLike(String user_id, int start, int size);
    List<Article> findArticleByOwn(String user_id, int start, int size);

    int insertArticleByAdmin(String article_id, String uploader_id, String uploader_name,
                             String uploader_avatar, String title, String classify,
                             String content);

    int insertArticleByAdmin(String article_id, String uploader_id, String uploader_name,
                             String uploader_avatar, String title, String classify,
                             String content, List<String> covers);

    int answerTopic(String article_id, String topic_id, String uploader_id,
                    String uploader_name, String uploader_avatar, String title,
                    String classify, String content);

    int answerTopicUser(String article_id, String topic_id, String uploader_id,
                        String title, String classify, String content);

    int insertArticleByUser(String article_id, String uploader_id, String title,
                            String classify, String content, List<String> covers);

    int deleteArticle(String id);
    int updateArticleBrowse(String article);
    List<Article> findArticleWithLikesByAuthorId(String user_id, String author_id, int start, int size);
}