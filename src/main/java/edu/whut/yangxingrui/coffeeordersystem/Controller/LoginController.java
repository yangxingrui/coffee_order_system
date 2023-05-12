package edu.whut.yangxingrui.coffeeordersystem.Controller;

import edu.whut.yangxingrui.coffeeordersystem.mapper.ManagerMapper;
import edu.whut.yangxingrui.coffeeordersystem.model.Manager;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Slf4j
@Controller
public class LoginController {

    @Autowired
    private ManagerMapper managerMapper;

    @GetMapping(path = "/login")
    public ModelAndView showCreateForm(HttpSession session) {
        List<Manager> manager = managerMapper.findAll();
        session.setAttribute("name",manager.get(0).getName());
        return new ModelAndView("login");
    }

    @PostMapping("/login")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password){
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(name, password));
            System.out.println("登录成功!");
        } catch (AuthenticationException e) {
            System.out.println("登录失败!");
        }
        return "redirect:/manager/";
    }

//    @GetMapping(path = "/error")
//    public ModelAndView showError() {
//        System.out.println("执行到此");
//        return new ModelAndView("error");
//    }

}
