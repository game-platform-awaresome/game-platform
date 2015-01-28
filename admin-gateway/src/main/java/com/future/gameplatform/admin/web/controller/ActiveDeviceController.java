package com.future.gameplatform.admin.web.controller;

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
 * Created by johnKee on 2015/1/24.
 */
@Controller
@RequestMapping(value = "/active")
public class ActiveDeviceController {

    private final static Logger logger = LoggerFactory.getLogger(ActiveDeviceController.class);

    @Autowired
    private SettleService settleService;


    @RequestMapping(value = "/list")
    public String listActiveDeviceAccount(HttpServletRequest request, Model model){
        logger.debug("list active device statistic info");
        model.addAttribute("cps", settleService.getCPList());
        String op = request.getParameter("op");
        if(op != null && op.equals("q")) {
            String selectedCp = request.getParameter("cp");
            String beginDate = request.getParameter("beginDate");
            String endDate = request.getParameter("endDate");
            if(!checkDateRange(beginDate, endDate)){
                model.addAttribute("errMsg","日期格式不对或不是一个有效的时间段！");
                return "active/deviceAccount";
            }
            model.addAttribute("devices", settleService.getActiveDeviceAccount(selectedCp, beginDate, endDate));

            model.addAttribute("selectedCp", selectedCp);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
            model.addAttribute("op", op);
        }else {
            Date nowDate = new Date();

            long myTime=(nowDate.getTime()/1000)-60*60*24*30;

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String selectedCp = "all_multi";
            String endDate = simpleDateFormat.format(nowDate);
            nowDate.setTime(myTime*1000);
            String beginDate = simpleDateFormat.format(nowDate);
            model.addAttribute("selectedCp", selectedCp);
            model.addAttribute("beginDate", beginDate);
            model.addAttribute("endDate", endDate);
        }
        return "active/deviceAccount";
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
