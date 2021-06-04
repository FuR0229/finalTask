package chat;

import chat.mapper.UserMapper;
import chat.entity.User;
import cn.hutool.json.JSONObject;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
@MapperScan("chat.mapper")
public class DemoApplication {
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
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
	@Bean
	public JSONObject jsonObject(){
		return new JSONObject();
	}

}
