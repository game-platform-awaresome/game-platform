package com.future.gameplatform.admin.web.controller;

import com.future.gameplatform.account.game.UserAccountString;
import com.future.gameplatform.account.game.entity.AccountItem;
import com.future.gameplatform.account.game.entity.RechargeAppAccount;
import com.future.gameplatform.account.game.service.RechargeAppAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-9-25
 * Time: 下午5:26
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping(value = "/channel")
public class ChannelController {

    private static final Logger logger = LoggerFactory.getLogger(ChannelController.class) ;

    @Autowired
    private RechargeAppAccountService rechargeAppAccountService;


    @RequestMapping(value = "/list")
    public String listCPConfig(HttpServletRequest request, Model model){
        logger.debug("list all configed cps");
        List<RechargeAppAccount> accountList = rechargeAppAccountService.listAll();
        model.addAttribute("accounts", accountList);
        return "channel/list";
    }

    @RequestMapping(value = "/config1step")
    public String config1Step(HttpServletRequest request, Model model){
        logger.debug("begin config one new cp, step 1");
        return "channel/config1step";
    }

    @RequestMapping(value = "/config1step/{id}")
    public String config1StepEdit(@PathVariable("id") String id, HttpServletRequest request, Model model){
        logger.debug("begin config one old cp, step 1,id:[{}]",id);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(id);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/config1stepedit";
    }

    @RequestMapping(value = "/doconfig1step", method = RequestMethod.POST )
    public String doConfig1Step(HttpServletRequest request, Model model){
        logger.debug("do config first step");
        String cpName = request.getParameter("cpName");
        String shortcode = request.getParameter("shortcode");
        String noticeurl = request.getParameter("noticeurl");
        if(StringUtils.isEmpty(noticeurl)){
            noticeurl = "http://gateway.tonggewang.com/0/api/rechargenotice/cp/notice/demo";
        }
        String appkey = rechargeAppAccountService.getKeyByCode(shortcode);
        if(appkey != null){
            model.addAttribute("cpName", cpName);
            model.addAttribute("shortcode",shortcode);
            model.addAttribute("noticeurl",noticeurl);
            model.addAttribute("error","短码已经存在，请录入一个新的短码");
            return "channel/config1step";
        }
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cpName",cpName);
        params.put("shortcode", shortcode);
        params.put("noticeurl", noticeurl);
        String accountId = rechargeAppAccountService.insert(params);

        return "redirect:/channel/config2step/"+accountId;
    }


    @RequestMapping(value = "/doconfig1stepedit/{id}", method = RequestMethod.POST )
    public String doConfig1StepEdit(@PathVariable("id")String id, HttpServletRequest request, Model model){
        logger.debug("do config first step edit");
        String cpName = request.getParameter("cpName");
        String noticeurl = request.getParameter("noticeurl");
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("cpName",cpName);
        params.put("noticeurl", noticeurl);
        String accountId = rechargeAppAccountService.updateRechargeApp(id, params);

        return "redirect:/channel/config2step/"+accountId;
    }

    @RequestMapping(value = "/config2step/{id}")
    public String config2step(@PathVariable("id") String id, HttpServletRequest request, Model model){
        logger.debug("begin config one old cp, step 2,id [{}]", id);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(id);
        model.addAttribute("account", rechargeAppAccount);
        if(id.equalsIgnoreCase(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID)){
            return "channel/showdetail";
        }
        return "channel/cpconfigDetail";
    }

    @RequestMapping(value = "/{id}/doConfig2step/status/{channel}")
    public String doConfig2StepStatus(@PathVariable("id") String id, @PathVariable("channel")String channel, HttpServletRequest request, Model model){
        logger.debug("do config 2 step status,id:[{}] channel:[{}]",id,channel);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(id);
        List<AccountItem> itemList = rechargeAppAccount.getItems();
        Iterator<AccountItem> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            AccountItem item = itemIterator.next();
            if(channel.equalsIgnoreCase(item.getChannel())){
                if(item.getStatus().equalsIgnoreCase(UserAccountString.STATUS_OK)){
                    item.setStatus(UserAccountString.STATUS_DELETED);
                }else {
                    item.setStatus(UserAccountString.STATUS_OK);
                }
                rechargeAppAccountService.updateAccountChannel(id, channel, item);
            }
        }
        logger.debug("do config 2 step status,id:[{}] channel:[{}],result ok", id, channel);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/cpconfigDetail";
    }

    @RequestMapping(value = "/{id}/doConfig2step/sortcode/{channel}/{vchange}")
    public String doConfig2StepSortcode(@PathVariable("id") String id, @PathVariable("channel")String channel, @PathVariable("vchange") int vchange, HttpServletRequest request, Model model){
        logger.debug("change id:[{}] config channel [{}],sortcode [{}]", channel, vchange);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(id);
        List<AccountItem> itemList = rechargeAppAccount.getItems();
        Iterator<AccountItem> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            AccountItem item = itemIterator.next();
            if(channel.equalsIgnoreCase(item.getChannel())){
                item.setSortcode(item.getSortcode() + vchange);
                rechargeAppAccountService.updateAccountChannel(id, channel, item);
            }
        }
        logger.debug("change id:[{}] config channel [{}],sortcode [{}],result ok", channel, vchange);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/cpconfigDetail";
    }


    @RequestMapping(value = "/defaultConfig")
    public String getDefaulstConfig(HttpServletRequest request, Model model){
        logger.debug("show default config info");
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/showdetail";
    }

    @RequestMapping(value = "/defaultConfig/statua/{channel}")
    public String changeDefaulstConfigStatus(@PathVariable("channel")String channel,HttpServletRequest request, Model model){
        logger.debug("change default config channel [{}] status",channel);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID);
        List<AccountItem> itemList = rechargeAppAccount.getItems();
        Iterator<AccountItem> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            AccountItem item = itemIterator.next();
            if(channel.equalsIgnoreCase(item.getChannel())){
                if(item.getStatus().equalsIgnoreCase(UserAccountString.STATUS_OK)){
                    item.setStatus(UserAccountString.STATUS_DELETED);
                }else {
                    item.setStatus(UserAccountString.STATUS_OK);
                }
                rechargeAppAccountService.updateOrInsertDefaultAccountChannel(item);
            }
        }
        logger.debug("change default config channel [{}] status,result ok",channel);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/showdetail";
    }

    @RequestMapping(value = "/defaultConfig/sortcode/{channel}/{vchange}")
    public String changeDefaulstConfigSortcode(@PathVariable("channel")String channel,@PathVariable("vchange")int vchange,HttpServletRequest request, Model model){
        logger.debug("change default config channel [{}],sortcode [{}]", channel, vchange);
        RechargeAppAccount rechargeAppAccount = rechargeAppAccountService.getById(UserAccountString.RECHARGE_ACCOUNT_DEFAULT_ID);
        List<AccountItem> itemList = rechargeAppAccount.getItems();
        Iterator<AccountItem> itemIterator = itemList.iterator();
        while (itemIterator.hasNext()){
            AccountItem item = itemIterator.next();
            if(channel.equalsIgnoreCase(item.getChannel())){
                item.setSortcode(item.getSortcode() + vchange);
                rechargeAppAccountService.updateOrInsertDefaultAccountChannel(item);
            }
        }
        logger.debug("change default config channel [{}],sortcode [{}],result ok", channel, vchange);
        model.addAttribute("account", rechargeAppAccount);
        return "channel/showdetail";
    }

}
