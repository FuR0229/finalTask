package chat.Service;

import chat.mapper.UserMapper;
import chat.entity.User;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;
import java.util.List;

@Service("producerService")
public class messageService {
    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsTemplate jmsTemplate;
    @Autowired private UserMapper userMapper;


    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage( String room,final String message){
        Destination destination = new ActiveMQQueue(room);
        jmsTemplate.convertAndSend(destination, message);
    }

    public List<User> checkUserPwd(Integer id, Integer password) {
        // TODO Auto-generated method stub
        return userMapper.checkUserPwd(id,password);
    }
    public User getById(int id) {
        // TODO Auto-generated method stub
        return userMapper.selectByPrimaryKey(id);
    }
    public String receiveMessage(String destination) throws JMSException {
        Object msg=  jmsTemplate.receiveAndConvert(destination);
        if (msg instanceof String) {
           // System.out.println("Received: " + msg);
            return (String)msg;
        }
        return null;
    }

}
