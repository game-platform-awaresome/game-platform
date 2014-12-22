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
import java.text.ParseException;
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
            if(!checkDateRange(beginDate, endDate)){
                model.addAttribute("errMsg","日期格式不对或不是一个有效的时间段！");
                return "settle/list";
            }
            if(selectedCp.equals("all_multi") && selectedChannel.equals("all_multi"))  {
                model.addAttribute("errMsg", "不能同时分CP和渠道统计！") ;
                return "settle/list";
            }
            model.addAttribute("settles", settleService.getSettle(selectedCp, selectedChannel, beginDate, endDate));

            model.addAttribute("selectedCp", selectedCp);
            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }else {
            Date nowDate = new Date();

            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String selectedCp = "all_multi";
            String selectedChannel = "all_single";
            String endDate = simpleDateFormat.format(nowDate);
            nowDate.setTime(myTime*1000);
            String beginDate = simpleDateFormat.format(nowDate);
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

            if(!checkDateRange(beginDate, endDate)){
                model.addAttribute("errMsg","日期格式不对或不是一个有效的时间段！");
                return "settle/cpList";
            }

            model.addAttribute("settles", settleService.getSettle(user.getOrganizationId(), selectedChannel, beginDate, endDate));

            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);

        }else {
            Date nowDate = new Date();

            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            String selectedChannel = "all_multi";
            String endDate = simpleDateFormat.format(nowDate);
            nowDate.setTime(myTime*1000);
            String beginDate = simpleDateFormat.format(nowDate);

            model.addAttribute("selectedChannel", selectedChannel);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }
        return "settle/cpList";
    }

    @RequestMapping(value = "/supple")
    public String orderSupple(HttpServletRequest request, Model model){
        logger.debug("order supple");
        String id = request.getParameter("id");
        String msg = settleService.suppleOrder(id);
        model.addAttribute("msg", msg);
        return "settle/supple";
    }

    private boolean checkDateRange(String begindate, String enddate) {
        if(begindate == null || enddate == null)
            return false;
        try{
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            simpleDateFormat.parse(begindate);
            simpleDateFormat.parse(enddate);

        } catch (ParseException e) {
            return false;
        }
        if(begindate.compareTo(enddate) > -1) {
            return false;
        }
        return true;
    }

}
