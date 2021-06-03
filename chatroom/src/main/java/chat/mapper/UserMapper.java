package chat.mapper;

import chat.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    List<User> checkUserPwd(@Param("username")Integer username, @Param("password")Integer password);

    User selectByPrimaryKey(int id);
}
