package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.admin.entity.User;
import com.future.gameplatform.admin.service.UserService;
import com.future.gameplatform.common.id.IdFactory;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        String organizationId = (String)req.getParameter("organizationId");
        String username = (String) req.getParameter("username");
        String passwd = (String) req.getParameter("password");
        String passwdRe = (String)req.getParameter("repassword");
        if(!passwd.equals(passwdRe)){
            model.addAttribute("errorMsg", "密码不一致！");
            model.addAttribute("shortcode", organizationId);
            return "register1";
        }
        User user = new User();
        user.setId(IdFactory.generateId());
        user.setOrganizationId(organizationId);
        user.setUsername(username);
        user.setPassword(passwd);
        List<String> roleIds = new ArrayList<String>();
        roleIds.add("cp");
        user.setRoleIds(roleIds);
        userService.createUser(user);
        logger.debug("do reg success, should go to login");
        return "index";
    }

    @RequestMapping(value = "/changepwd/{username}")
    public String showChangePwdForm(HttpServletRequest request, Model model){
        logger.debug("request change admin password");
        return "changePwd";
    }

    @RequestMapping(value = "/changepwd/{username}", method = RequestMethod.POST)
    public String doChangePwd(String oldPwd, String pwd, String rePwd, @PathVariable("username")String username,Model model){
        logger.debug("do change {} password.", username);
        String error = null;
        if(StringUtils.isEmpty(pwd) || StringUtils.isEmpty(rePwd) || StringUtils.isEmpty(oldPwd)){
            error = "密码不能为空！";
        } else if(!pwd.equalsIgnoreCase(rePwd)){
            error = "新密码与确认密码不一致！";
        }else {
            String changeResult = userService.validAndChangePassword(username, oldPwd, pwd);
            if(changeResult.equalsIgnoreCase("success"))  {
                return "redirect:/logout";
            }
            error = changeResult;
        }
        model.addAttribute("error", error);
        return "changePwd";
    }


}
