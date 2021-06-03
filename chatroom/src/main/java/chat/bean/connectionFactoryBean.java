package chat.bean;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

public class connectionFactoryBean {
    @Bean
    public javax.jms.ConnectionFactory connectionFactory(){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL("tcp://localhost:61616");
        connectionFactory.setUserName("admin");
        connectionFactory.setPassword("admin");
        return connectionFactory;
    }
    @Bean
    public JmsTemplate genJmsTemplate(){
        System.out.println("aaaaaaaaaaaaaaaaaaaaaabbbbbbbbb");
        return new JmsTemplate(connectionFactory());
    }
    @Bean
    public JmsMessagingTemplate jmsMessageTemplate(){
        System.out.println("ccccccccccccc");
        return new JmsMessagingTemplate(connectionFactory());
    }
    @Bean
    public Destination destination(){
        return new Destination() {
            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
    @Bean
    public MessageProducer messageProducer(){
        return new MessageProducer() {
            @Override
            public void setDisableMessageID(boolean b) throws JMSException {

            }

            @Override
            public boolean getDisableMessageID() throws JMSException {
                return false;
            }

            @Override
            public void setDisableMessageTimestamp(boolean b) throws JMSException {

            }

            @Override
            public boolean getDisableMessageTimestamp() throws JMSException {
                return false;
            }

            @Override
            public void setDeliveryMode(int i) throws JMSException {

            }

            @Override
            public int getDeliveryMode() throws JMSException {
                return 0;
            }

            @Override
            public void setPriority(int i) throws JMSException {

            }

            @Override
            public int getPriority() throws JMSException {
                return 0;
            }

            @Override
            public void setTimeToLive(long l) throws JMSException {

            }

            @Override
            public long getTimeToLive() throws JMSException {
                return 0;
            }

            @Override
            public Destination getDestination() throws JMSException {
                return null;
            }

            @Override
            public void close() throws JMSException {

            }

            @Override
            public void send(Message message) throws JMSException {

            }

            @Override
            public void send(Message message, int i, int i1, long l) throws JMSException {

            }

            @Override
            public void send(Destination destination, Message message) throws JMSException {

            }

            @Override
            public void send(Destination destination, Message message, int i, int i1, long l) throws JMSException {

            }
        };
    }
    @Bean
    public MessageConsumer messageConsumer(){
        return new MessageConsumer() {
            @Override
            public String getMessageSelector() throws JMSException {
                return null;
            }

            @Override
            public MessageListener getMessageListener() throws JMSException {
                return null;
            }

            @Override
            public void setMessageListener(MessageListener messageListener) throws JMSException {

            }

            @Override
            public Message receive() throws JMSException {
                return null;
            }

            @Override
            public Message receive(long l) throws JMSException {
                return null;
            }

            @Override
            public Message receiveNoWait() throws JMSException {
                return null;
            }

            @Override
            public void close() throws JMSException {

            }
        };
    }

}
