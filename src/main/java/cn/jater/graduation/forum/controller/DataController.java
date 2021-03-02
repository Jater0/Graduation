package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.utils.message.MessageHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/data")
public class DataController {
    @PostMapping("/browse_all/{user_id}/{browse_all}")
    public MessageHandler updateBrowseAll(@PathVariable String user_id, @PathVariable int browse_all) {
        System.out.println(browse_all);
        return new MessageHandler<>(200, "");
    }
}
