package cn.jater.graduation.forum.service;

import cn.jater.graduation.forum.entries.AdminRole;

import java.util.List;

public interface AdminRoleService {
    List<AdminRole> findAllAdminRole();
    List<Integer> findAllAdminRoleId();
}
