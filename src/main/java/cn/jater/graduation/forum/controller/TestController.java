package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.*;
import cn.jater.graduation.forum.service.impl.*;
import cn.jater.graduation.forum.utils.id.IDHandler;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import cn.jater.graduation.forum.utils.time.TimeFormatHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/test")
    public MessageHandler test() {
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(TimeFormatHandler.getNow());
        }).start();
        new Thread(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(TimeFormatHandler.getNow());
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(TimeFormatHandler.getNow());
        return new MessageHandler<>(200, TimeFormatHandler.getNow());
    }

//    @PostMapping("/test02")
//    public MessageHandler test02() {
//
//    }
}