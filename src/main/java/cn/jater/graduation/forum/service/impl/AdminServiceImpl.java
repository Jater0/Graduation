package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Admin;
import cn.jater.graduation.forum.mapper.AdminMapper;
import cn.jater.graduation.forum.service.AdminService;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public Admin findAdminByLogin(String account, String password) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        Admin admin = mapper.findAdminByLogin(account, password);
        sqlSession.commit();
        sqlSession.close();
        return admin;
    }

    @Override
    public int updateAdminInfo(String id, String email, String phone, String name) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        int updateCode = mapper.updateAdminInfo(id, email, phone, name);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }

    @Override
    public int updateAdminExplain(String id, String explain) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        int updateCode = mapper.updateAdminExplain(id, explain);
        sqlSession.commit();
        sqlSession.close();
        return updateCode;
    }

    @Override
    public List<Admin> findAllAdminWithoutFinder(String id) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        List<Admin> admins = mapper.findAllAdminWithoutFinder(id);
        sqlSession.commit();
        sqlSession.close();
        return admins;
    }

    @Override
    public List<Admin> findAllAdmin() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        List<Admin> admins = mapper.findAllAdmin();
        sqlSession.commit();
        sqlSession.close();
        return admins;
    }

    @Override
    public int createAdmin(String id, String name, String email, String password, int level) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        AdminMapper mapper = sqlSession.getMapper(AdminMapper.class);
        int insertCode = mapper.createAdmin(id, name, email, password, level);
        sqlSession.commit();
        sqlSession.close();
        return insertCode;
    }
}
