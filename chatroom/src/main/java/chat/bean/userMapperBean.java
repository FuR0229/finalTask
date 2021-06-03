package chat.bean;

import chat.mapper.UserMapper;
import chat.entity.User;
import org.springframework.context.annotation.Bean;

import java.util.List;

public class userMapperBean {
    @Bean
    public UserMapper userMapper(){
        return new UserMapper() {
            @Override
            public List<User> checkUserPwd(Integer username, Integer password) {
                return null;
            }

            @Override
            public User selectByPrimaryKey(int id) {
                return null;
            }
        };
    }
}
