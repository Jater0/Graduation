package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Feedback;

import java.sql.Timestamp;
import java.util.List;

public interface FeedbackService {
    List<Feedback> findFeedbackAll();
    List<String> findFeedbackCoverByFeedbackId(String id);
    int insertFeedback(String id, String user_id, String content, int feedback_type, Timestamp create_time, List<String> covers);
}
