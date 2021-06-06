package chat.Service;

import chat.mapper.UserMapper;
import chat.entity.User;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.config.SimpleJmsListenerEndpoint;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.jms.*;
import java.io.*;
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

    public String receiveMessage(String destination) throws JMSException, IOException {
        Object msg=  jmsTemplate.receiveAndConvert(destination);
        if (msg instanceof String) {
           // System.out.println("Received: " + msg);
            return (String)msg;
        }
        else{
            Destination destination1=new ActiveMQQueue(destination);
            receiveFile(destination1);
            return "upload file success";
        }
       /*ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        final Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination destination1 = session.createQueue(destination);
        MessageConsumer consumer = session.createConsumer(destination1);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(textMessage.getText());
                    }
                    else{
                        receiveFile(destination1);
                    }
                }catch (JMSException | IOException e) {
                    e.printStackTrace();
                }
            }
        });
        return null;*/
    }
    public void sendFile(String room, MultipartFile file) throws JMSException, IOException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        //2.创建Connection
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = new ActiveMQQueue(room);
        //3.启动连接
        connection.start();
        MessageProducer producer = session.createProducer(destination);
        StreamMessage message=session.createStreamMessage();
        message.setStringProperty("COMMAND","start");
        producer.send(message);
        //开始发送
        byte[] content=new byte[4096];
        InputStream ins= file.getInputStream();
        BufferedInputStream bins=new BufferedInputStream(ins);
        while(bins.read(content)>0){
            message=session.createStreamMessage();
            message.setStringProperty("FILE_NAME",file.getOriginalFilename());
            message.setStringProperty("COMMAND","sending");
            message.clearBody();
            message.writeBytes(content);
            producer.send(message);
        }
        bins.close();
        ins.close();
        //发送完毕
        message=session.createStreamMessage();
        message.setStringProperty("COMMAND","end");
        producer.send(message);
        System.out.println("完成文件发送："+file.getOriginalFilename());
    }
    public static void receiveFile(Destination destination) throws JMSException, IOException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        ConnectionFactory factory=new ActiveMQConnectionFactory("tcp://127.0.0.1:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //Destination destination=session.createTopic("File.Transport");
        MessageConsumer consumer=session.createConsumer(destination);
        boolean appended=false;
        try{
            while(true){
                Message message=consumer.receive(5000);
                if(message==null){
                    continue;
                }
                if(message instanceof StreamMessage){
                    StreamMessage streamMessage=(StreamMessage)message;
                    String command=streamMessage.getStringProperty("COMMAND");
                    if("start".equals(command)){
                        appended=false;
                        continue;
                    }
                    if("sending".equals(command)){
                        byte[] content=new byte[4096];
                        String file_name=message.getStringProperty("FILE_NAME");
                        BufferedOutputStream bos=null;
                        bos=new BufferedOutputStream(new FileOutputStream("C:\\Users\\DELL\\Desktop\\"+file_name,appended));
                        if(!appended){
                            appended=true;
                        }
                        while(streamMessage.readBytes(content)>0){
                            bos.write(content);
                        }
                        bos.close();
                        continue;
                    }
                    if("end".equals(command)){
                        appended=false;
                        break;
                    }
                }
            }
        }catch (JMSException e){
            throw e;
        }finally {
            /*if(connection!=null){
                connection.close();*/
        }
    }

}
