package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.*;
import cn.jater.graduation.forum.service.impl.*;
import cn.jater.graduation.forum.utils.constant.OSSConstant;
import cn.jater.graduation.forum.utils.id.IDHandler;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import cn.jater.graduation.forum.utils.oss.PhotoHandler;
import cn.jater.graduation.forum.utils.time.TimeFormatHandler;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;


/**
 * Use this apis when user login
 */
@RestController
@RequestMapping("/forum")
public class MainController {
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

    @Autowired
    ArticleServiceImpl articleService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    PhotoHandler photoHandler;

    @Autowired
    FeedbackServiceImpl feedbackService;

    @RequestMapping("/get_user")
    public MessageHandler getUserInfo(@RequestParam String id) {
        User user = userService.findUserByWeChatId(id);
        try {
            System.out.println(user.get_id());
            return new MessageHandler<>(200, user);
        } catch (NullPointerException e) {
            return new MessageHandler<>(404, "userInfo Not Found");
        }
    }

    @RequestMapping("/create_user")
    public MessageHandler createUser(@RequestParam String id, String name, String avatar, String gender) {
        try {
            new Thread(() -> {
                String avatar_oss = avatar;
                avatar_oss = photoHandler.downloadPicture(avatar_oss, OSSConstant.avatarFile);
                userService.createUser(id, name, avatar_oss, gender);
            }).start();
            return new MessageHandler<>(200, "create new account success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "create new account failed");
        }
    }

    @RequestMapping("/get_label")
    public MessageHandler getLabelWithUserId(@RequestParam String id) {
        try {
            List<UserLabel> data = userLabelService.findUserLabelByUserId(id);
            return new MessageHandler<>(200, data);
        } catch (Exception e) {
            return new MessageHandler<>(200, "get label failed");
        }
    }

    @RequestMapping("/update_label")
    public MessageHandler updateLabelWithUserId(@RequestParam String id, String updates) {
        String convert = updates.replace("[", "(").replace("]", ")");
        userLabelService.updateUserLabelForUserId(id, convert);
        return new MessageHandler<>(200, updates);
    }

    @RequestMapping("/update_like")
    public MessageHandler updateLikeWithUserId(@RequestParam String id, String article) {
        try {
            userArticleLikesService.updateArticleLikesByUserIdAndArticleId(id, article);
            return new MessageHandler<>(200, "update like success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update like failed");
        }
    }

    @GetMapping("/get_list/{id}/{name}/{page}/{size}")
    public MessageHandler getListWithUserId(@PathVariable("id") String id, @PathVariable("name") String name,
                                            @PathVariable("page") int page, @PathVariable("size") int size) {
        try {
            List<UserTopic> output = userTopicService.findTopicWithLikesByUserId(id, name, (page - 1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get list failed");
        }
    }

    @GetMapping("/get_following/{id}/{page}/{size}")
    public MessageHandler getFollowingWithUserId(@PathVariable("id") String id,
                                                 @PathVariable("page")int page,
                                                 @PathVariable("size") int size) {
        try {
            List<UserTopic> output = userTopicService.findTopicWithFollowingByUserId(id, (page-1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get following failed");
        }
    }

    @GetMapping("/get_article/{id}/{topic}/{page}/{size}")
    public MessageHandler getArticle(@PathVariable("id") String id, @PathVariable("topic") String topic,
                                     @PathVariable("page")int page, @PathVariable("size") int size) {
        try {
            List<Article> output = articleService.findArticleDetailByUserIdAndTopicId(id, topic, (page - 1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get article failed");
        }
    }

    @RequestMapping("/author_like")
    public MessageHandler updateAuthorLikes(@RequestParam String id, String author) {
        try {
            userAuthorLikesService.updateAuthorLikesByUserIdAndAuthorId(id, author);
            return new MessageHandler<>(200, "update author likes success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update author likes failed");
        }
    }

    @RequestMapping("update_thumbsup")
    public MessageHandler updateArticleThumbs(@RequestParam String id, String article) {
        try {
            int output = userArticleLikesService.updateArticleThumbsByUserIdAndArticleId(id, article);
            if (output == 0) {
                return new MessageHandler<>(200, "点赞成功");
            } else {
                return new MessageHandler<>(200, "你已经点过赞了");
            }
        } catch (Exception e) {
            return new MessageHandler<>(500, "update thumbs up failed");
        }
    }

    @RequestMapping("/get_comment")
    public MessageHandler getComment(@RequestParam String article) {
        try {
            List<Comment> output = commentService.findCommentByArticleId(article);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get comment failed");
        }
    }

    @RequestMapping("/update_comment")
    public MessageHandler updateComment(String cid, String content,int reply, String father, String aid, String name, String avatar, String to, String to_uid) {
        try {
            commentService.insertNewComment(cid, content, TimeFormatHandler.getNow(), reply, father, aid, name, avatar, to, to_uid);
            return new MessageHandler<>(200, "update comment success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update comment failed");
        }
    }

    @RequestMapping("/get_topic")
    public MessageHandler getTopicById(@RequestParam String id) {
        try {
            Topic topic = topicService.findTopicByTopicId(id);
            return new MessageHandler<>(200, topic);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get topic failed");
        }
    }

    @RequestMapping("/update_topic")
    public MessageHandler updateTopic(String uid, String title, String classify, String covers) {
        try {
            List<String> convert = JSON.parseArray(covers, String.class);
            int insertSuccess = topicService.insertTopicWithSqlFuncWithCovers(IDHandler.getRandomID("tp", 8), uid, title, classify, TimeFormatHandler.getNow(), convert);
            if (insertSuccess >= 1) {
                return new MessageHandler<>(200, "update topic success");
            } else {
                return new MessageHandler<>(500, "update topic failed");
            }
        } catch (Exception e) {
            return new MessageHandler<>(500, "update topic failed");
        }
    }

    @RequestMapping("/update_feedback")
    public MessageHandler updateFeedback(@RequestParam String id, String content, int type, String covers) {
        try {
            List<String> convert = JSON.parseArray(covers, String.class);
            int insertSuccess = feedbackService.insertFeedback(IDHandler.getRandomID("fb", 8), id, content, type, TimeFormatHandler.getNow(), convert);
            return new MessageHandler<>(200, insertSuccess);
        } catch (Exception e) {
            return new MessageHandler<>(500, "update feedback failed");
        }
    }

    @RequestMapping(value = "/create_article", method = RequestMethod.POST)
    public MessageHandler createArticleUser(String id, String title, String classify, String content, String covers) {
        try {
            List<String> convertCovers = JSON.parseArray(covers, String.class);
            int insertCode = articleService.insertArticleByUser(
                    IDHandler.getRandomID("art", 8),
                    id,
                    title,
                    classify,
                    content,
                    convertCovers);
            if (insertCode == 0) return new MessageHandler<>(500, "create article failed");
            else return new MessageHandler<>(200, "create article success");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "create article failed");
        }
    }

    @RequestMapping("/answer_topic")
    public MessageHandler answerTopicUser(String topic, String id, String type, String classify, String content) {
        try {
            int insertCode = articleService.answerTopicUser(
                    IDHandler.getRandomID("tp", 8),
                    topic,
                    id,
                    type,
                    classify,
                    content);
            if (insertCode == 0) return new MessageHandler<>(500, "answer topic failed");
            else return new MessageHandler<>(200, "answer topic success");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(200, "answer topic success");
        }
    }

    @GetMapping("/get_recommend/{id}/{page}/{size}")
    public MessageHandler getRecommend(@PathVariable("id") String id, @PathVariable("page")int page, @PathVariable("size") int size) {
        try {
            return new MessageHandler<>(200, "get recommend list success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "get recommend list failed");
        }
    }

    @GetMapping("/get_article_like/{id}/{page}/{size}")
    public MessageHandler getArticleLike(@PathVariable("id") String id, @PathVariable("page") int page, @PathVariable("size") int size) {
        try {
            List<Article> articles = articleService.findArticleByUserLike(id, (page - 1) * size, size);
            return new MessageHandler<>(200, articles);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get article by user likes failed");
        }
    }

    @GetMapping("/get_article_own/{id}/{page}/{size}")
    public MessageHandler getArticleOwn(@PathVariable("id") String id,
                                        @PathVariable("page") int page,
                                        @PathVariable("size") int size) {
        try {
            List<Article> articles = articleService.findArticleByOwn(id, (page - 1) * size, size);
            return new MessageHandler<>(200, articles);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get article own failed");
        }
    }

    @GetMapping("/get_topic_own/{id}/{page}/{size}")
    public MessageHandler getTopicOwn(@PathVariable("id") String id,
                                      @PathVariable("page") int page,
                                      @PathVariable("size") int size) {
        try {
            return new MessageHandler<>(200, "get topic own success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "get topic own failed");
        }
    }
}