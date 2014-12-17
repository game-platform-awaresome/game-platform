package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.admin.Constants;
import com.future.gameplatform.admin.entity.User;
import com.future.gameplatform.admin.service.RechargeQueryService;
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
 * Date: 14-12-14
 * Time: 下午8:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/recharge/query")
public class RechargeQueryController {

    private static final Logger logger = LoggerFactory.getLogger(RechargeQueryController.class);

    @Autowired
    private RechargeQueryService rechargeQueryService;

    @RequestMapping(value = "/order")
    public String queryRecharge(HttpServletRequest httpServletRequest, Model model){
        logger.debug("admin query order info");
        String mobile = httpServletRequest.getParameter("mobile");
        String orderno = httpServletRequest.getParameter("orderno");
        String id = httpServletRequest.getParameter("id");
        String begindate = httpServletRequest.getParameter("begindate");
        String enddate = httpServletRequest.getParameter("enddate");
        String op = httpServletRequest.getParameter("op");
        if(op != null && op.equals("q")){
            if(!checkDateRange(begindate, enddate)){
                model.addAttribute("errMsg","日期格式不对或不是一个有效的时间段！");
                return "orderquery/adminorder";
            }
            model.addAttribute("mobile", mobile);
            model.addAttribute("orderno", orderno);
            model.addAttribute("id", id);
            model.addAttribute("begindate", begindate);
            model.addAttribute("enddate", enddate);
            model.addAttribute("orders", rechargeQueryService.queryOrder(null, mobile, orderno, id, begindate, enddate));
        }else {
            Date nowDate = new Date();
            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("enddate", simpleDateFormat.format(nowDate));
            nowDate.setTime(myTime);
            model.addAttribute("begindate", simpleDateFormat.format(nowDate));
        }
        return "orderquery/adminorder";
    }

    @RequestMapping(value = "/cp/order")
    public String queryCpRecharge(HttpServletRequest httpServletRequest, Model model){
        logger.debug("cp user query order info");
        User user = (User)httpServletRequest.getSession().getAttribute(Constants.CURRENT_USER);
        String mobile = httpServletRequest.getParameter("mobile");
        String orderno = httpServletRequest.getParameter("orderno");
        String id = httpServletRequest.getParameter("id");
        String begindate = httpServletRequest.getParameter("begindate");
        String enddate = httpServletRequest.getParameter("enddate");
        String op = httpServletRequest.getParameter("op");
        if(op != null && op.equals("q")){
            if(!checkDateRange(begindate, enddate)){
                model.addAttribute("errMsg","日期格式不对或不是一个有效的时间段！");
                return "orderquery/adminorder";
            }
            model.addAttribute("mobile", mobile);
            model.addAttribute("orderno", orderno);
            model.addAttribute("id", id);
            model.addAttribute("begindate", begindate);
            model.addAttribute("enddate", enddate);
            model.addAttribute("orders", rechargeQueryService.queryOrder(user.getOrganizationId(), mobile, orderno, id, begindate, enddate));
        }else {
            Date nowDate = new Date();
            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            model.addAttribute("enddate", simpleDateFormat.format(nowDate));
            nowDate.setTime(myTime);
            model.addAttribute("begindate", simpleDateFormat.format(nowDate));
        }
        return "orderquery/adminorder";
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
