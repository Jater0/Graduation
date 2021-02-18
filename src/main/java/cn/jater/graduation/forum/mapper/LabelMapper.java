package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.Label;
import cn.jater.graduation.forum.service.LabelService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface LabelMapper extends LabelService {
    @Override
    @Insert({"insert into label(_id, label_name, create_time) values (#{_id}, #{name}, #{create_time})"})
    int insertNewLabel(Label label);

    @Override
    @Select({"select * from label"})
    List<Label> findAll();
}