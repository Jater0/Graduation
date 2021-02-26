package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.Admin;
import cn.jater.graduation.forum.entries.Feedback;
import cn.jater.graduation.forum.entries.Label;
import cn.jater.graduation.forum.service.impl.*;
import cn.jater.graduation.forum.utils.id.IDHandler;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;

    @Autowired
    FeedbackServiceImpl feedbackService;

    @Autowired
    LabelServiceImpl labelService;

    @Autowired
    TopicServiceImpl topicService;

    @Autowired
    AdminRoleServiceImpl adminRoleService;

    @Autowired
    ArticleServiceImpl articleService;

    @RequestMapping("/login")
    public MessageHandler doLogin(String account, String password) {
        try {
            Admin admin = adminService.findAdminByLogin(account, password);
            return new MessageHandler<>(200, admin);
        } catch (Exception e) {
            return new MessageHandler<>(500, "login action failed");
        }
    }

    @RequestMapping("updateInfo")
    public MessageHandler updateAdminInfo(String id, String email, String phone, String name) {
        try {
            int updateCode = adminService.updateAdminInfo(id, email, phone, name);
            if (updateCode == 0) {
                return new MessageHandler<>(200, "update admin info success");
            } else {
                return new MessageHandler<>(202, "please change email or phone");
            }
       } catch (Exception e) {
            return new MessageHandler<>(500, "update admin info failed");
        }
    }

    @RequestMapping("/updateExplain")
    public MessageHandler updateExplain(String id, String explain) {
        try {
            adminService.updateAdminExplain(id, explain);
            return new MessageHandler<>(200, "update explain success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update explain failed");
        }
    }

    @GetMapping("/get_admins/{page}/{size}")
    public MessageHandler findAll(@PathVariable("page") int page,@PathVariable("size") int size) {
        try {
            Map<String, Object> admins = adminService.findAllAdminWithPage((page - 1) * size, size);
            if (admins.isEmpty()) {
                return new MessageHandler<>(404, "get admins nullable");
            }
            return new MessageHandler<>(200, admins);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get admins failed");
        }
    }

    @RequestMapping("/get_admins/by")
    public MessageHandler findAdminWithoutId(String id) {
        try {
            List<Admin> admins = adminService.findAllAdminWithoutFinder(id);
            if (admins.isEmpty()) {
                return new MessageHandler<>(404, "get admins nullable");
            }
            return new MessageHandler<>(200, admins);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get admins failed");
        }
    }

    @GetMapping("/get_users/{page}/{size}")
    public MessageHandler findAllUser(@PathVariable("page") int page,@PathVariable("size") int size) {
        try {
            Map<String, Object> users = userService.findAllUserWithCount((page - 1) * size, size);
            if (users.isEmpty()) {
                return new MessageHandler<>(404, "get users nullable");
            }
            return new MessageHandler<>(200, users);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get users failed");
        }
    }

    @RequestMapping("/create_admin")
    public MessageHandler createAdmin(String id, String email ,String name, String password, int level) {
        try {
            int insertCode = adminService.createAdmin(id, name, email, password, level);
            if (insertCode == 1) {
                return new MessageHandler<>(202, "email is exist on the database");
            }
            return new MessageHandler<>(200, "create admin success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "create admin failed");
        }
    }

    @RequestMapping("/update_avatar")
    public MessageHandler updateAdminAvatar(String id, String avatar) {
        try {
            adminService.updateAvatarById(id, avatar);
            return new MessageHandler<>(200, "update avatar success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update avatar failed");
        }
    }

    @GetMapping("/update_gender/{id}/{gender}")
    public MessageHandler updateAdminGender(@PathVariable("id") String id, @PathVariable("gender") String gender) {
        try {
            adminService.updateGenderByID(id, gender);
            return new MessageHandler<>(200, "update gender success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update gender failed");
        }
    }

    @RequestMapping("/get_feedback")
    public MessageHandler getFeedbackIsCheck(int page, int size) {
        try {
            List<Feedback> feedbackList = feedbackService.findFeedbackIsCheck((page - 1) * size, size);
            return new MessageHandler<>(200, feedbackList);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get feedback failed");
        }
    }

    @RequestMapping("/get_feedback/by")
    public MessageHandler getFeedbackByType(int type, int page, int size) {
        try {
            List<Feedback> feedbackList = feedbackService.findFeedbackByType(type,(page - 1) * size, size);
            return new MessageHandler<>(200, feedbackList);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get feedback failed");
        }
    }

    @RequestMapping("/update_feedback")
    public MessageHandler updateFeedback(String id, int work) {
        try {
            int updateCode = feedbackService.updateFeedbackState(id, work);
            if (updateCode == 0) {
                return new MessageHandler<>(300, "update feedback warning, cause others admin updated");
            } else if (updateCode == 1) {
                return new MessageHandler<>(200, "update feedback success");
            } else {
                return new MessageHandler<>(200, "update feedback success and update user integral");
            }
        } catch (Exception e) {
            return new MessageHandler<>(500, "update feedback failed");
        }
    }

    @RequestMapping("/get_label")
    public MessageHandler getLabelForAdmin() {
        try {
            List<Label> labels = labelService.findAll();
            return new MessageHandler<>(200, labels);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get label for admin failed");
        }
    }

    @RequestMapping("/create_topic")
    public MessageHandler createTopicWithAdmin(String content, String type, String covers) {
        try {
            List<String> convertCovers = JSON.parseArray(covers, String.class);
            int insertCode = topicService.insertTopicWithAdmin(
                    IDHandler.getRandomID("tp", 8),
                    "admins",
                    "官方账号",
                    "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/adminDefault.png",
                    content,
                    type,
                    convertCovers);
            if (insertCode > 0)
                return new MessageHandler<>(200, "create topic success");
            else
                return new MessageHandler<>(500, "create topic failed");
        } catch (Exception e) {
            return new MessageHandler<>(500, "create topic failed");
        }
    }

    @RequestMapping("/get_list/role_id")
    public MessageHandler getRoleIdList() {
        try {
            List<Integer> adminRoleIds = adminRoleService.findAllAdminRoleId();
            return new MessageHandler<>(200, adminRoleIds);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get role id list failed");
        }
    }

    @RequestMapping(value = "/create_article", method = RequestMethod.POST)
    public MessageHandler createArticleWithAdmin(String title, String classify, String content, String covers) {
        try {
            List<String> convertCovers = JSON.parseArray(covers, String.class);
            System.out.println(convertCovers);
            int insertCode = articleService.insertArticleByAdmin(
                    IDHandler.getRandomID("art", 8),
                    "admins",
                    "官方账号",
                    "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/adminDefault.png",
                    title,
                    classify,
                    content,
                    convertCovers
            );
            if (insertCode == 0) return new MessageHandler<>(500, "create article failed");
            return new MessageHandler<>(200, "create article success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "create article failed");
        }
    }

    @RequestMapping(value = "/answer_topic", method = RequestMethod.POST)
    public MessageHandler answerTopicWithAdmin(String topic, String title, String classify, String content) {
        try {
            int insertCode = articleService.answerTopic(
                    IDHandler.getRandomID("tp", 8),
                    topic,
                    "admins",
                    "官方账号",
                    "https://objective-forum-oss.oss-cn-shenzhen.aliyuncs.com/forum-avatar/adminDefault.png",
                    title,
                    classify, content
            );
            if (insertCode == 0) return new MessageHandler<>(500, "answer topic failed");
            else return new MessageHandler<>(200, "answer topic success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "answer topic failed");
        }
    }
}