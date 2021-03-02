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
import java.util.List;
import java.util.Map;


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

    @Autowired
    UserFollowServiceImpl userFollowService;

    @GetMapping("/get_user/{id}")
    public MessageHandler getUserInfo(@PathVariable("id") String id) {
        User user = userService.findUserByWeChatId(id);
        try {
            System.out.println(user.get_id());
            return new MessageHandler<>(200, user);
        } catch (NullPointerException e) {
            return new MessageHandler<>(404, "userInfo Not Found");
        }
    }

    @RequestMapping("/create_user")
    public MessageHandler createUser(String id, String name, String avatar, String gender) {
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

    @GetMapping("/get_label/{id}")
    public MessageHandler getLabelWithUserId(@PathVariable("id") String id) {
        try {
            List<UserLabel> data = userLabelService.findUserLabelByUserId(id);
            return new MessageHandler<>(200, data);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get label failed");
        }
    }

    @RequestMapping("/update_label")
    public MessageHandler updateLabelWithUserId(String id, String updates) {
        try {
            String convert = updates.replace("[", "(").replace("]", ")");
            userLabelService.updateUserLabelForUserId(id, convert);
            return new MessageHandler<>(200, updates);
        } catch (Exception e) {
            return new MessageHandler<>(500, "update label failed");
        }
    }

    @GetMapping("/update_like/{id}/{article}")
    public MessageHandler updateLikeWithUserId(@PathVariable("id") String id,
                                               @PathVariable("article") String article) {
        try {
            userArticleLikesService.updateArticleLikesByUserIdAndArticleId(id, article);
            return new MessageHandler<>(200, "update like success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update like failed");
        }
    }

    /**
     * 获取Topic列表By Classify
     * @param id
     * @param name
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/get_list/{id}/{name}/{page}/{size}")
    public MessageHandler getListWithUserId(@PathVariable("id") String id,
                                            @PathVariable("name") String name,
                                            @PathVariable("page") int page,
                                            @PathVariable("size") int size) {
        try {
            List<UserTopic> output = userTopicService.findTopicWithLikesByUserId(id, name, (page - 1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get list failed");
        }
    }

    /**
     * 获取用户关注的用户的文章列表
     * @param id 请求的用户的ID
     * @param page 页数
     * @param size 数量
     * @return
     */
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
    public MessageHandler getArticle(@PathVariable("id") String id,
                                     @PathVariable("topic") String topic,
                                     @PathVariable("page")int page,
                                     @PathVariable("size") int size) {
        try {
            List<Article> output = articleService.findArticleDetailByUserIdAndTopicId(id, topic, (page - 1) * size, size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get article failed");
        }
    }

    @GetMapping("/get_topic_article/{id}/{article}/{page}/{size}")
    public MessageHandler getTopicArticle(@PathVariable("id") String id,
                                          @PathVariable("article") String article,
                                          @PathVariable("page") int page,
                                          @PathVariable("size") int size) {
        try {
            List<Article> output = articleService.findArticleDetailByUserIdAndArticleId(id, article, (page - 1) * size , size);
            return new MessageHandler<>(200, output);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get article failed");
        }
    }

    @GetMapping("/author_like/{id}/{author}")
    public MessageHandler updateAuthorLikes(@PathVariable("id") String id,
                                            @PathVariable("author") String author) {
        try {
            userAuthorLikesService.updateAuthorLikesByUserIdAndAuthorId(id, author);
            return new MessageHandler<>(200, "update author likes success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update author likes failed");
        }
    }

    @GetMapping("/update_thumbsup/{id}/{article}")
    public MessageHandler updateArticleThumbs(@PathVariable("id") String id,
                                              @PathVariable("article") String article) {
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

    @GetMapping("/get_comment/{article}")
    public MessageHandler getComment(@PathVariable("article") String article) {
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

    @GetMapping("/get_topic/{id}")
    public MessageHandler getTopicById(@PathVariable("id") String id) {
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
    public MessageHandler updateFeedback(String id, String content, int type, String covers) {
        try {
            List<String> convert = JSON.parseArray(covers, String.class);
            int insertSuccess = feedbackService.insertFeedback(IDHandler.getRandomID("fb", 8), id, content, type, TimeFormatHandler.getNow(), convert);
            return new MessageHandler<>(200, insertSuccess);
        } catch (Exception e) {
            return new MessageHandler<>(500, "update feedback failed");
        }
    }

    @PostMapping("/create_article/{id}/{classify}")
    public MessageHandler createArticleUser(@PathVariable("id") String id,
                                            @PathVariable("classify") String classify,
                                            @RequestBody Map<String, Object> data) {
        try {
            int insertCode = articleService.insertArticleByUser(
                    IDHandler.getRandomID("art", 8),
                    id,
                    (String)data.get("title"),
                    classify,
                    (String) data.get("content"),
                    (List<String>) data.get("covers"));
            if (insertCode == 0) return new MessageHandler<>(500, "create article failed");
            else return new MessageHandler<>(200, "create article success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "create article failed");
        }
    }

    @PostMapping("/answer_topic/{id}/{topic_id}/{classify}")
    public MessageHandler answerTopicUser(@PathVariable("id") String id,
                                          @PathVariable("topic_id") String topic_id,
                                          @PathVariable("classify") String classify,
                                          @RequestBody Map<String, String> data) {
        try {
            int insertCode = articleService.answerTopicUser(
                    IDHandler.getRandomID("tp", 8),
                    topic_id,
                    id,
                    data.get("title"),
                    classify,
                    data.get("content"));
            if (insertCode == 0) return new MessageHandler<>(500, "answer topic failed");
            else return new MessageHandler<>(200, "answer topic success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "answer topic failed");
        }
    }

    @GetMapping("/get_recommend/{id}/{page}/{size}")
    public MessageHandler getRecommend(@PathVariable("id") String id,
                                       @PathVariable("page")int page,
                                       @PathVariable("size") int size) {
        try {
            return new MessageHandler<>(200, "get recommend list success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "get recommend list failed");
        }
    }

    @GetMapping("/get_article_like/{id}/{page}/{size}")
    public MessageHandler getArticleLike(@PathVariable("id") String id,
                                         @PathVariable("page") int page,
                                         @PathVariable("size") int size) {
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
            List<Topic> topics = topicService.findTopicOwn(id, (page - 1) * size, size);
            return new MessageHandler<>(200, topics);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get topic own failed");
        }
    }

    @GetMapping("/get_fans_user/{id}/{page}/{size}")
    public MessageHandler getFans(@PathVariable("id")String id,
                                  @PathVariable("page") int page,
                                  @PathVariable("size") int size) {
        try {
            List<UserFollow> userFans = userFollowService.findFansDetailByUserId(id, (page - 1) * size, size);
            return new MessageHandler<>(200, userFans);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get fans failed");
        }
    }

    @GetMapping("/get_following_user/{id}/{page}/{size}")
    public MessageHandler getFollowing(@PathVariable("id")String id,
                                       @PathVariable("page") int page,
                                       @PathVariable("size") int size) {
        try {
            List<UserFollow> userFollowing = userFollowService.findFollowingDetailByUserId(id, (page - 1) * size, size);
            return new MessageHandler<>(200, userFollowing);
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "get following failed");
        }
    }

    @PostMapping("/delete/{mode}/{id}")
    public MessageHandler deleteArticle(@PathVariable("mode") String mode,
                                        @PathVariable("id") String id) {
        try {
            if (mode.equals("article")) {
                articleService.deleteArticle(id);
            } else if (mode.equals("topic")) {
                topicService.deleteTopic(id);
            }
            return new MessageHandler<>(200, "delete article success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "delete article failed");
        }
    }

    @PostMapping("/update_gender/{id}/{gender}")
    public MessageHandler updateGender(@PathVariable("id") String id,
                                       @PathVariable("gender") String gender) {
        try {
            userService.updateGender(id, gender);
            return new MessageHandler<>(200, "update gender success");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "update gender failed");
        }
    }

    @PostMapping("/update_explain/{id}")
    public MessageHandler updateExplain(@PathVariable("id") String id,
                                        @RequestBody Map<String, String> data) {
        try {
            String explain = data.get("explain");
            userService.updateExplain(id, explain);
            return new MessageHandler<>(200, "update explain success");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "update explain failed");
        }
    }

    @PostMapping("/update_professional/{id}")
    public MessageHandler updateProfessional(@PathVariable("id") String id,
                                             @RequestBody Map<String, String> data) {
        try {
            String professional = data.get("professional");
            userService.updateProfessional(id, professional);
            return new MessageHandler<>(200, "update professional success");
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "update professional failed");
        }
    }

    @GetMapping("/get_hot_list")
    public MessageHandler getHotList() {
        try {
            List<Map<String, String>> hotList = topicService.findHotListLimitIIX();
            return new MessageHandler<>(200, hotList);
        } catch (Exception e) {
            e.printStackTrace();
            return new MessageHandler<>(500, "get hot list failed");
        }
    }

    @PostMapping("/update_article_browse/{article}")
    public MessageHandler updateArticleBrowse(@PathVariable("article") String article) {
        try {
            articleService.updateArticleBrowse(article);
            return new MessageHandler<>(200, "update browse success");
        } catch (Exception e) {
            return new MessageHandler<>(500, "update browse failed");
        }
    }

    @GetMapping("/get_article_others/{user}/{others}/{page}/{size}")
    public MessageHandler getArticleOthers(@PathVariable("user") String user,
                                        @PathVariable("others") String others,
                                        @PathVariable("page") int page,
                                        @PathVariable("size") int size) {
        try {
            List<Article> articles = articleService.findArticleWithLikesByAuthorId(user, others, (page - 1) * size, size);
            return new MessageHandler<>(200, articles);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get others info failed");
        }
    }

    @GetMapping("/get_info_others/{user}/{others}")
    public MessageHandler getInfoOthers(@PathVariable("user") String user,
                                        @PathVariable("others") String others) {
        try {
            UserFollow userFollow = userFollowService.findOthersDetailByUserId(user, others);
            return new MessageHandler<>(200, userFollow);
        } catch (Exception e) {
            return new MessageHandler<>(500, "get others info failed");
        }
    }
}