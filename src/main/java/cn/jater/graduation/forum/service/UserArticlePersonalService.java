package cn.jater.graduation.forum.service;

import java.util.List;

public interface UserArticlePersonalService {
    List<String> findUserArticlePersonalArticleIdByUserId(String user_id);
}
