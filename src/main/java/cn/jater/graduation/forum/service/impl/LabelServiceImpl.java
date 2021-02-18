package cn.jater.graduation.forum.service.impl;

import cn.jater.graduation.forum.entries.Label;
import cn.jater.graduation.forum.mapper.LabelMapper;
import cn.jater.graduation.forum.service.LabelService;
import cn.jater.graduation.forum.utils.data.DataHandler;
import cn.jater.graduation.forum.utils.mybatis.MyBatisHandler;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelServiceImpl implements LabelService {
    @Override
    public int insertNewLabel(Label label) {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        LabelMapper mapper = sqlSession.getMapper(LabelMapper.class);
        int insertAction = mapper.insertNewLabel(label);
        sqlSession.commit();
        sqlSession.close();
        return insertAction;
    }

    @Override
    public List<Label> findAll() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        LabelMapper mapper = sqlSession.getMapper(LabelMapper.class);
        List<Label> labels = mapper.findAll();
        sqlSession.commit();
        sqlSession.close();
        return labels;
    }

    @Override
    public List<JSONObject> findLabelAllWithStateless() {
        SqlSession sqlSession = MyBatisHandler.getSqlSession();
        LabelMapper mapper = sqlSession.getMapper(LabelMapper.class);
        List<Label> labels = mapper.findAll();
        sqlSession.commit();
        sqlSession.close();
        List<JSONObject> output = new DataHandler().dataLabelPlusCurrentStateless(labels);
        return output;
    }
}