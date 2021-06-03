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

import javax.jms.*;
import java.util.List;

@Service("producerService")
public class producerService {
    @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsTemplate;
    @Autowired private UserMapper userMapper;


    // 发送消息，destination是发送到的队列，message是待发送的消息
    public void sendMessage( final String message){
        Destination destination = new ActiveMQQueue("mytest.queue");
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
   // @JmsListener(destination = "mytest.queue")
    public void receiveMessage() throws InterruptedException {
        SimpleJmsListenerEndpoint endpoint = new SimpleJmsListenerEndpoint();
        endpoint.setId("mytest.queue");
        endpoint.setDestination("mytest.queue");
        endpoint.setMessageListener(message -> {
            try {
                String text = ((TextMessage)message).getText();
                System.out.println(Thread.currentThread().getName()+"获取到的消息为："+text);
                Thread.sleep(1000);
            } catch (InterruptedException | JMSException e) {
                System.out.println("消息读取失败"+e.getMessage());
            }
        });
    }

}
