package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.UserFollow;

import java.util.List;

public interface UserFollowService {
    List<UserFollow> findFollowingDetailByUserId(String id, int start, int size);
    List<UserFollow> findFansDetailByUserId(String id, int start, int size);
    UserFollow findOthersDetailByUserId(String user_id, String author_id);
}
