package cn.jater.graduation.forum.mapper;

import cn.jater.graduation.forum.entries.UserLabel;
import cn.jater.graduation.forum.service.UserLabelService;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserLabelMapper extends UserLabelService {
    @Override
    @Select({"select * from user_label where user_id = #{user_id}"})
    List<UserLabel> findUserLabelByUserId(String user_id);

    @Override
    @Update({"update user_label set " +
            "current = case " +
            "when(current=0) " +
            "then 1 " +
            "else 0 end " +
            "where user_id = #{user_id} and " +
            "label_name in ${updates}"})
    int updateUserLabelForUserId(String user_id, String updates);

    @Override
    @Insert({"insert into user_label(user_id, label_name) values ${data}"})
    int insertUserLabelWhenSignUp(String data);
}