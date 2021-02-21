package cn.jater.graduation.forum.controller;

import cn.jater.graduation.forum.entries.Admin;
import cn.jater.graduation.forum.entries.User;
import cn.jater.graduation.forum.service.impl.AdminServiceImpl;
import cn.jater.graduation.forum.service.impl.UserServiceImpl;
import cn.jater.graduation.forum.utils.message.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @Autowired
    UserServiceImpl userService;

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

    @RequestMapping("/get_admins")
    public MessageHandler findAll() {
        try {
            List<Admin> admins = adminService.findAllAdmin();
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

    @RequestMapping("/get_users")
    public MessageHandler findAllUser(int page, int size) {
        try {
            List<User> users = userService.findAllUser((page - 1) * size, size);
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
}
