package chat.controller;

import chat.Service.messageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Controller
public class loginController {

    @ResponseBody
    @RequestMapping(value = "/loginPage", method = {RequestMethod.POST, RequestMethod.GET})
    public String login(HttpServletRequest request, HttpSession session) {
        String tno = request.getParameter("tno");
        String password1 = request.getParameter("password");
        session.setAttribute("name",tno);
        Integer username=Integer.parseInt(tno);
        Integer password=Integer.parseInt(password1);
        System.out.println("你输入的账号为：" + tno);
        System.out.println("你输入的密码为：" + password);
        return "<script language=\"javascript\">window.location.href=\"/chat\"</script>";
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


    @RequestMapping(value="/chat")
    public String show(){
        return "chat";
    }

}
