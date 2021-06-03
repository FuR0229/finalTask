package chat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service("consumerService")
public class consumerService {
   /* @Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    //private MessageConsumer consumer;
    public void recieveMessage(Session session,Destination destination) throws JMSException {
        consumer=session.createConsumer(destination);
        String returnMessage=null;
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if(message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(textMessage.getText());
                    }
                    else {

                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
}
