package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Article;

import java.util.List;

public interface ArticleService {
    List<Article> findArticleDetailByUserIdAndTopicId(String user_id, String topic_id, int start, int pageSize);
}
