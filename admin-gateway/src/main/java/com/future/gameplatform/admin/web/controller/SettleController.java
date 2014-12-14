package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.admin.Constants;
import com.future.gameplatform.admin.entity.User;
import com.future.gameplatform.admin.service.SettleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-9-25
 * Time: 下午5:48
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/settle")
public class SettleController {

    private static final Logger logger = LoggerFactory.getLogger(SettleController.class);

    @Autowired
    private SettleService settleService;


    @RequestMapping(value = "/list")
    public String listSettle(HttpServletRequest httpServletRequest, Model model){
        logger.debug("list settle report");
        model.addAttribute("cps", settleService.getCPList());
        model.addAttribute("channels", settleService.getTemplateChannelList());
        String op = httpServletRequest.getParameter("op");
        if(op != null && op.equals("q")){
            String selectedCp = httpServletRequest.getParameter("cp");
            String selectedChannel = httpServletRequest.getParameter("channel");
            String beginDate = httpServletRequest.getParameter("beginDate");
            String endDate = httpServletRequest.getParameter("endDate");

            model.addAttribute("settles", settleService.getSettle(selectedCp, selectedChannel, beginDate, endDate));

            model.addAttribute("selectedCp", selectedCp);
            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }else {
            Date nowDate = new Date();

            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
            String selectedCp = "all";
            String selectedChannel = "all";
            String beginDate = simpleDateFormat.format(nowDate);
            nowDate.setTime(myTime*1000);
            String endDate = simpleDateFormat.format(nowDate);
            model.addAttribute("selectedCp", selectedCp);
            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }

        return "settle/list";
    }

    @RequestMapping(value = "/cp/list")
    public String listCpSettle(HttpServletRequest httpServletRequest, Model model){
        logger.debug("list cp settle report");
        User user = (User)httpServletRequest.getSession().getAttribute(Constants.CURRENT_USER);
        model.addAttribute("channels", settleService.getTemplateChannelList());
        String op = httpServletRequest.getParameter("op");

        if(op != null && op.equals("q")){
            String selectedChannel = httpServletRequest.getParameter("channel");
            String beginDate = httpServletRequest.getParameter("beginDate");
            String endDate = httpServletRequest.getParameter("endDate");

            model.addAttribute("settles", settleService.getSettle(user.getOrganizationId(), selectedChannel, beginDate, endDate));

            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }else {
            Date nowDate = new Date();

            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");

            String selectedChannel = "all";
            String beginDate = simpleDateFormat.format(nowDate);
            nowDate.setTime(myTime*1000);
            String endDate = simpleDateFormat.format(nowDate);

            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }
        return "settle/cpList";
    }

}
