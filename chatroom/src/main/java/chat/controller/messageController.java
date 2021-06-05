package chat.controller;

import chat.Service.messageService;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jms.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.hutool.json.JSONObject;

@Controller
@RequestMapping("/message")
public class messageController {
    @Autowired
    private messageService producer;
    @Autowired
    JSONObject object = new JSONObject();

    String room=null;

    public messageController(messageService producer) {
        this.producer = producer;
    }

    @ResponseBody
    @RequestMapping(value = "/getResult",method = RequestMethod.POST)
    public Object getResult(@RequestParam String result,HttpSession session,HttpServletResponse response) throws IOException {
        this.room=result;
        System.out.println(session.getAttribute("name") + "加入了"+this.room);
        object.put("msg","ok");
        return object;
    }

    @ResponseBody
    @RequestMapping(value = "/send",method=RequestMethod.POST)
    public Object send(@RequestParam String message, HttpServletRequest request, HttpSession session)  {
        //String message = request.getParameter("input");
        Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message=formatter.format(calendar.getTime())+" :"+message;
        producer.sendMessage(this.room,message);
        System.out.println(session.getAttribute("name") + "  "+message);
        object.put("msg","ok");
        //response.getWriter().write(String.valueOf(object));
        //return "<script language=\"javascript\">window.location.href=\"/chat\"</script>";
        return object;
    }

    @ResponseBody
    @RequestMapping(value = "/sendFile",method = RequestMethod.POST)
    public String sendFile(HttpServletRequest request,HttpServletResponse response , HttpSession session) throws JMSException, IOException {
        String message = request.getParameter("input");
        Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message=formatter.format(calendar.getTime())+" :"+message;
        producer.sendMessage(room,message);
        producer.receiveMessage(room);
        System.out.println(session.getAttribute("name") + "  "+producer.receiveMessage(room));
        //object.put("msg",producer.receiveMessage("mytest.queue"));
        //response.getWriter().write(String.valueOf(object));
        //return "<script language=\"javascript\">window.location.href=\"/chat\"</script>";
        return producer.receiveMessage(room);
    }

    @ResponseBody
    @RequestMapping(value = "/receive",method = RequestMethod.GET)
    public String receive(HttpSession session) throws JMSException{
        /*ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        final Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        final Destination destination = session.createQueue(room);
        MessageConsumer consumer = session.createConsumer(destination);
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        System.out.println(textMessage.getText());
                    }
                }catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });*/
        return session.getAttribute("name") + "  "+producer.receiveMessage(room);
    }
}
