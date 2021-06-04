package chat.controller;

import chat.Service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.jms.JMSException;
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

    public messageController(messageService producer) {
        this.producer = producer;
    }

    @ResponseBody
    @RequestMapping(value = "/send",method = RequestMethod.POST)
    public String send(HttpServletRequest request,HttpServletResponse response , HttpSession session) throws JMSException, IOException {
        String message = request.getParameter("input");
        Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message=formatter.format(calendar.getTime())+" :"+message;
        producer.sendMessage(message);
        producer.receiveMessage("mytest.queue");
        System.out.println(session.getAttribute("name") + "  "+message);
        //object.put("msg",producer.receiveMessage("mytest.queue"));
        //response.getWriter().write(String.valueOf(object));
        //return "<script language=\"javascript\">window.location.href=\"/chat\"</script>";
        return producer.receiveMessage("mytest.queue");
    }
}
