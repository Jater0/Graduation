package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.UserArticleLikes;

import java.util.List;

public interface UserArticleLikesService {
    List<UserArticleLikes> findUserArticleLikesThumbsByUserId(String user_id);
    List<String> findUserArticleLikesByUserId(String user_id);
    List<String> findUserArticleThumbsByUserId(String user_id);
    void updateArticleLikesByUserIdAndArticleId(String user_id, String article_id);
    int updateArticleThumbsByUserIdAndArticleId(String user_id, String article_id);
}