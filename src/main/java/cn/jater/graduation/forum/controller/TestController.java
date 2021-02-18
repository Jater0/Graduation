package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.*;
import cn.jater.graduation.forum.service.impl.*;
import cn.jater.graduation.forum.utils.id.IDHandler;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    UserLabelServiceImpl userLabelService;

    @Autowired
    LabelServiceImpl labelService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserAuthorLikesServiceImpl userAuthorLikesService;

    @Autowired
    UserArticleLikesServiceImpl userArticleLikesService;

    @Autowired
    UserArticlePersonalServiceImpl userArticlePersonalService;

    @Autowired
    TopicServiceImpl topicService;

    @Autowired
    TopicArticleServiceImpl topicArticleService;

    @Autowired
    FeedbackServiceImpl feedbackService;

    @RequestMapping("/get_label")
    public MessageHandler<List<UserLabel>> getLabel(@RequestParam String id) {
        List<UserLabel> data = userLabelService.findUserLabelByUserId(id);
        return new MessageHandler<>(200, data);
    }

    @RequestMapping("/update_label")
    public MessageHandler<String> updateLabel(@RequestParam String id, String updates) {
        String data = "(\"Java\",\"Python\")";
        userLabelService.updateUserLabelForUserId(id, data);
        return new MessageHandler<>(200, "update label success");
    }

    @RequestMapping("/update_user_label")
    public String test() {
        List<String> list = new ArrayList<>();
        list.add("Java");
        list.add("Python");
        list.add("Android");
        list.add("IOS");
        list.add("人工智能");
        list.add("前端开发");
        list.add("算法");
        String id = "chen47testid";
        StringBuffer sb = new StringBuffer();
        int listSize = list.size();
        for (int i = 0; i < listSize; i++) {
            sb.append("(\"").append(id).append("\",\"").append(list.get(i)).append("\")");
            if (i != listSize - 1) {
                sb.append(",");
            }
        }
        userLabelService.insertUserLabelWhenSignUp(sb.toString());
        return sb.toString();
    }

    @RequestMapping("/new_label")
    public String insertNewLabel() {
        List<String> data = new ArrayList<>();
//
//        data.add("Java");
//        data.add("Python");
//
//        data.add("Android");
//        data.add("IOS");
//
//        data.add("人工智能");
//        data.add("前端开发");
//        data.add("算法");
        for (int i= 0; i < data.size(); i++) {
            String _id = IDHandler.getUid();
            Label label = new Label(_id, data.get(i), new Timestamp(System.currentTimeMillis()));
            labelService.insertNewLabel(label);
        }
        return "OK";
    }

    @RequestMapping("/label_all")
    public MessageHandler findAll() {
        return new MessageHandler<>(200, labelService.findAll());
    }

    @RequestMapping("/get_user")
    public MessageHandler getUserInfo(@RequestParam String id) {
        User user = userService.findUserByWeChatId(id);
        return new MessageHandler<>(200, user);
    }

    @RequestMapping("/get_author_like")
    public MessageHandler getAuthorLike(@RequestParam String id) {
        List<UserAuthorLikes> authorLikes = userAuthorLikesService.findUserAuthorLikesByUserId(id);
        return new MessageHandler<>(200, authorLikes);
    }

    @RequestMapping("/get_author_like_id")
    public MessageHandler getAuthorLikeId(@RequestParam String id) {
        List<String> output = userAuthorLikesService.findUserAuthorLikesAuthorIdByUserId(id);
        return new MessageHandler<>(200, output);
    }

    @RequestMapping("/get_article_self_id")
    public MessageHandler getArticleSelfId(@RequestParam String id) {
        List<String> output = userArticlePersonalService.findUserArticlePersonalArticleIdByUserId(id);
        return new MessageHandler<>(200, output);
    }

    @RequestMapping("/get_article_like")
    public MessageHandler getArticleLikes(@RequestParam String id) {
        List<UserArticleLikes> articleLikes = userArticleLikesService.findUserArticleLikesThumbsByUserId(id);
        return new MessageHandler<>(200, articleLikes);
    }

    @RequestMapping("/get_topic")
    public MessageHandler getTopic(@RequestParam String id) {
        Topic topic = topicService.findTopicByTopicId(id);
        return new MessageHandler<>(200, topic);
    }

    @RequestMapping("/get_content")
    public MessageHandler getContent(@RequestParam String content) {
        System.out.println(content);
        return new MessageHandler<>(200, content);
    }

    @RequestMapping("/get_feedback")
    public MessageHandler getFeedback() {
        List<Feedback> feedbackList = feedbackService.findFeedbackAll();
        return new MessageHandler<>(200, feedbackList);
    }

    @RequestMapping("/get_f_cover")
    public MessageHandler getFCover(@RequestParam String id) {
        List<String> covers = feedbackService.findFeedbackCoverByFeedbackId(id);
        return new MessageHandler<>(200, covers);
    }
}