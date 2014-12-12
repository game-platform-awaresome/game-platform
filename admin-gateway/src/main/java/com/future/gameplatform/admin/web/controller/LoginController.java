package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.admin.entity.User;
import com.future.gameplatform.admin.service.UserService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-2-15
 * <p>Version: 1.0
 */
@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/login")
    public String showLoginForm(HttpServletRequest req, Model model) {
        String exceptionClassName = (String)req.getAttribute("shiroLoginFailure");
        String error = null;
        if(UnknownAccountException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if(IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
            error = "用户名/密码错误";
        } else if("jCaptcha.error".equals(exceptionClassName)) {
            error = "验证码错误";
        } else if(exceptionClassName != null) {
            error = "其他错误：" + exceptionClassName;
        }
        model.addAttribute("error", error);
        return "login";
    }

    @RequestMapping(value = "/register0")
    public String showRegForm(HttpServletRequest req, Model model){
        logger.debug("enter register");
        //model.addAttribute("user", new User());
        return "register0";
    }

    @RequestMapping(value = "/register0", method = RequestMethod.POST)
    public String doRegValidOrg(HttpServletRequest req, Model model){
        logger.debug("do register step 1");
        String shortCode= (String) req.getParameter("shortCode");
        String key = (String) req.getParameter("key");
        if(!userService.orgAuth(shortCode, key)){
            model.addAttribute("errorMsg", "短码和key不匹配！");
            return "register0";
        }
        model.addAttribute("shortcode", shortCode);
        return "register1";
    }

    @RequestMapping(value = "/register1", method = RequestMethod.POST)
    public String doReg(HttpServletRequest req, Model model){
        logger.debug("do reg");
        String shortcode = (String)req.getParameter("shortcode");
        String username = (String) req.getParameter("username");
        String passwd = (String) req.getParameter("password");
        String passwdRe = (String)req.getParameter("repassword");
        if(!passwd.equals(passwdRe)){
            model.addAttribute("errorMsg", "密码不一致！");
            model.addAttribute("shortcode", shortcode);
            return "register1";
        }
        User user = new User();
        user.setOrganizationId(shortcode);
        user.setUsername(username);
        user.setPassword(passwd);
        userService.createUser(user);
        logger.debug("do reg success, should go to login");
        return "redirect:/login";
    }


}
