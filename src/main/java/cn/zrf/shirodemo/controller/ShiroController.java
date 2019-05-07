package cn.zrf.shirodemo.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.util.UUID;

@Controller
public class ShiroController {


    //@RequiresGuest ////表示当前Subject没有身份验证或者通过记住我登录过，即是游客身份
    @GetMapping(value = {"/login.html", "/"})
    public String login(Model model, HttpSession session){
        //生成一组16位的随机数作为盐值
        int hashcodeV = UUID.randomUUID().hashCode();
        if(hashcodeV < 0){
            hashcodeV = -hashcodeV;
        }
        String uuidSalt = String.format("%016d", hashcodeV);
        //把uuid的盐值，同时保存到前后端中
        model.addAttribute(uuidSalt);
        session.setAttribute("uuidSalt", uuidSalt);
        return "/login";
    }

    //@RequiresGuest
    @PostMapping(value = {"/login.html", "/"})
    public String login(String username, String password, Integer rememberme){
        //实现我们的登录验证，使用shiro
        //1.认证的核心组件，subject,获取subject对象
        Subject subject = SecurityUtils.getSubject();
        //2.登录验证的第二步，将表单提交过来的用户名和密码封装到token对象
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //开启记住我功能
        if(rememberme != null && rememberme == 1){
            token.setRememberMe(true);
        }
        //3.调用subject对象里的login方法，进行登录验证
        try {
            //让shiro帮助我们进行登录的验证，认证
            subject.login(token);
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return "/loginError";
        }
        return "redirect:/admin/main.html";
    }

    //@RequiresAuthentication //必须是登录成功才能访问，且不能通过记住我登录
    @GetMapping(value = "/admin/main.html")
    public String main(){
        return "/admin/main";
    }

    /**
     * 拥有userlist权限才能访问
     * Logical.OR 表示只要拥有权限中的某一个则可以访问
     * Logical.AND 需要所有的权限才能访问
     * @return
     */
    //@RequiresPermissions(value = {"userlist"}, logical = Logical.OR)
    @GetMapping(value = "/admin/userlist.html")
    public String userList(){
        return "/admin/userlist";
    }

    //@RequiresPermissions(value = "adduser")
    @GetMapping(value = "/admin/adduser.html")
    public String addUser(){
        return "/admin/adduser";
    }

    @GetMapping(value = "/unauthorized.html")
    public String unAuthorized(){
        return "/unauthorized";
    }

    /**
     * shiro使用注解以后要自己实现登出
     */
    /*@GetMapping(value = "logout.html")
        public String loginOut(){
        //拿到shiro主体对象，并调用logout实现登出
        SecurityUtils.getSubject().logout();
        return "redirect:/login.html";
    }*/
}
