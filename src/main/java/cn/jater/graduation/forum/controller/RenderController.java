package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.UserTopic;
import cn.jater.graduation.forum.service.impl.*;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/forum/stateless")
public class RenderController {
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
    UserTopicServiceImpl userTopicService;

    @RequestMapping("/get_label")
    public MessageHandler getLabelStateless() {
        try {
            List<JSONObject> output = labelService.findLabelAllWithStateless();
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get label failed");
        }
    }

    @RequestMapping("/get_list")
    public MessageHandler getListStateless(@RequestParam String name, int page, int size) {
        try {
            List<UserTopic> output = userTopicService.findTopicStateless(name, (page - 1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get list failed");
        }
    }
}
