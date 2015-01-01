package com.future.gameplatform.admin.service;

import com.future.gameplatform.common.service.RechargeRemoteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-14
 * Time: 下午7:38
 * To change this template use File | Settings | File Templates.
 */
@Service
public class SettleServiceImpl implements SettleService {

    @Autowired
    private RechargeHelper rechargeHelper;
    @Autowired
    private RechargeRemoteInterface rechargeRemoteInterface;

    @Override
    public Map<String, String> getCPList() {
        return rechargeHelper.doGetCpList();
    }

    @Override
    public Map<String, String> getTemplateChannelList() {
        return rechargeHelper.doGetTemplateChannelList();
    }

    @Override
    public List<Map<String, String>> getSettle(String selectedShortcode, String selectedChannel, String beginDate, String endDate) {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(endDate == null){
            endDate = simpleDateFormat.format(nowDate);
        }

        if(beginDate == null){
            nowDate.setTime(nowDate.getTime()-60*60*24*30*1000);
            beginDate = simpleDateFormat.format(nowDate);
        }

        return rechargeRemoteInterface.statisticRecharge(selectedShortcode, selectedChannel, beginDate, endDate);
    }

    @Override
    public String suppleOrder(String id) {
        if(StringUtils.isEmpty(id)){
            return "失败，参数无效！";
        }
        return rechargeRemoteInterface.suppleOrder(id);
    }

}
