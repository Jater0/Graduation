package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Admin;
import cn.jater.graduation.forum.service.AdminService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AdminMapper extends AdminService {
    @Override
    @Select({"select * from admin where (admin_email = #{account} or admin_phone = #{account} or _id = #{account}) and password = #{password}"})
    Admin findAdminByLogin(String account, String password);

    @Override
    @Select({"select update_admin(#{id}, #{email}, #{phone}, #{name})"})
    int updateAdminInfo(String id, String email, String phone, String name);

    @Override
    @Update({"update admin set `explain` = #{explain} where _id = #{id}"})
    int updateAdminExplain(String id, String explain);

    @Override
    @Select({"select * from admin where _id != #{id}"})
    List<Admin> findAllAdminWithoutFinder(String id);

    @Override
    @Select({"select * from admin"})
    List<Admin> findAllAdmin();

    @Override
    @Select({"select create_admin(#{id}, #{name}, #{email}, #{password}, #{level})"})
    int createAdmin(String id, String name, String email, String password, int level);
}
