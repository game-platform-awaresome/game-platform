package com.future.gameplatform.admin.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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


    @RequestMapping(value = "/list")
    public String listSettle(HttpServletRequest httpServletRequest, Model model){
        logger.debug("list settle report");
        return "settle/list";
    }
}
