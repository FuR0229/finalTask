package chat.controller;

import chat.Service.producerService;
import chat.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Controller
@RequestMapping("/producer")
public class producerController {
    @Autowired
    private producerService producer;
    @Autowired private producerService producerService;

    @ResponseBody
    @RequestMapping(value = "/loginPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(HttpServletRequest request, HttpSession session) {
        String tno = request.getParameter("tno");
        String password1 = request.getParameter("password");
        Integer username=Integer.parseInt(tno);
        Integer password=Integer.parseInt(password1);
        System.out.println("你输入的账号为：" + tno);
        System.out.println("你输入的密码为：" + password);
        return "<script language=\"javascript\">window.location.href=\"/producer/chat\"</script>";
        /*if (producerService.checkUserPwd(username, password).isEmpty()){
            return "<script language=\"javascript\">alert(\"用户名和密码不正确!\");window.location.href=\"/producer/login\"</script>";
        }
        else {
            session.setAttribute("tname", tno);
            return "<script language=\"javascript\">window.location.href=\"/producer/chat\"</script>";
        }*/
    }

    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public String login() {
        return "login";
    }


    @RequestMapping(value="/chat", method = {RequestMethod.POST, RequestMethod.GET})
    public String show(){
        return "chat";
    }
    @ResponseBody
    @RequestMapping(value = "/send", method = {RequestMethod.POST, RequestMethod.GET})
    public String send(HttpServletRequest request, HttpSession session, Model model) throws InterruptedException{
        producer.receiveMessage();
        String message = request.getParameter("input");
        Calendar calendar = Calendar.getInstance(); // gets current instance of the calendar
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        message=formatter.format(calendar.getTime())+" :"+message;
        producer.sendMessage(message);
        System.out.println("你发送的消息为：" + message);
        model.addAttribute("msg","message");
        producer.receiveMessage();
        return "<script language=\"javascript\">window.location.href=\"/producer/chat\"</script>";
    }
}
