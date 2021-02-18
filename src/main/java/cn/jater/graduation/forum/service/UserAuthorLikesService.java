package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.UserAuthorLikes;

import java.util.List;

public interface UserAuthorLikesService {
    List<UserAuthorLikes> findUserAuthorLikesByUserId(String user_id);
    List<String> findUserAuthorLikesAuthorIdByUserId(String user_id);
    void updateAuthorLikesByUserIdAndAuthorId(String user_id, String author_id);
}
