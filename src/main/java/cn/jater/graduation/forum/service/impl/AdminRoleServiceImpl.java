package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.AdminRole;
import cn.jater.graduation.forum.mapper.AdminRoleMapper;
import cn.jater.graduation.forum.service.AdminRoleService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminRoleServiceImpl implements AdminRoleService {

    @Override
    public List<AdminRole> findAllAdminRole() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminRoleMapper mapper = sqlSession.getMapper(AdminRoleMapper.class);
        List<AdminRole> adminRoleList = mapper.findAllAdminRole();
        sqlSession.commit();
        sqlSession.close();
        return adminRoleList;
    }

    @Override
    public List<Integer> findAllAdminRoleId() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminRoleMapper mapper = sqlSession.getMapper(AdminRoleMapper.class);
        List<Integer> adminRoleIds = mapper.findAllAdminRoleId();
        sqlSession.commit();
        sqlSession.close();
        return adminRoleIds;
    }
}