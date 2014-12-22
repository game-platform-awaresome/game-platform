package com.future.gameplatform.admin.service;

import com.future.gameplatform.common.service.RechargeRemoteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-14
 * Time: 下午8:32
 * To change this template use File | Settings | File Templates.
 */
@Service
public class RechargeQueryServiceImpl implements RechargeQueryService {

    @Autowired
    private RechargeHelper rechargeHelper;

    @Autowired
    private RechargeRemoteInterface rechargeRemoteInterface;

    @Override
    public List<Map<String, String>> queryOrder(String shortcode, String mobile, String orderno, String id, String begindate, String enddate) {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD");
        if(enddate == null){
            enddate = simpleDateFormat.format(nowDate);
        }

        if(begindate == null){
            nowDate.setTime(nowDate.getTime()-60*60*24*30*1000);
            begindate = simpleDateFormat.format(nowDate);
        }
        return rechargeRemoteInterface.queryRecharge(shortcode, mobile, orderno, id, begindate, enddate);
    }
}
