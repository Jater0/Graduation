package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.Admin;

import java.util.List;
import java.util.Map;

public interface AdminService {
    Admin findAdminByLogin(String account, String password);
    int updateAdminInfo(String id, String email, String phone, String name);
    int updateAdminExplain(String id, String explain);
    List<Admin> findAllAdminWithoutFinder(String id);
    List<Admin> findAllAdmin(int start, int size);
    Map<String, Object> findAllAdminWithPage(int start, int size);
    int createAdmin(String id, String name, String email, String password, int level);
    int updateAvatarById(String id, String avatar);
    int updateGenderByID(String id, String gender);
}
