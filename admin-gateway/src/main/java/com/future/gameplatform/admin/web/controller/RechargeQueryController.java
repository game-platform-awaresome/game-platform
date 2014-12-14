package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.admin.service.RechargeQueryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
        return null;
    }

    @RequestMapping(value = "/cp/order")
    public String queryCpRecharge(HttpServletRequest httpServletRequest, Model model){
        return null;
    }

}
