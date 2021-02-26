package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.AdminRole;
import cn.jater.graduation.forum.service.AdminRoleService;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AdminRoleMapper extends AdminRoleService {
    @Override
    @Select({"select * from admin_role"})
    List<AdminRole> findAllAdminRole();

    @Override
    @Select({"select role_id from admin_role"})
    List<Integer> findAllAdminRoleId();
}
