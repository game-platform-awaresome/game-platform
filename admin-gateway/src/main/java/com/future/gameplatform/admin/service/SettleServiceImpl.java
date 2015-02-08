package com.future.gameplatform.admin.service;

import com.future.gameplatform.common.service.ActiveDeviceRemoteInterface;
import com.future.gameplatform.common.service.RechargeRemoteInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private ActiveDeviceRemoteInterface activeDeviceRemoteInterface;

    @Override
    public Map<String, String> getCPList() {
        return rechargeHelper.doGetCpList();
    }

    @Override
    public Map<String, String> getTemplateChannelList() {
        Map<String, String> listMap = rechargeHelper.doGetTemplateChannelList();
        Map<String, String> collapseMap = collapseChannel(listMap);
        return collapseMap;
    }

    private Map<String, String> collapseChannel(Map<String, String> listMap) {
        Map<String, String> collresult = new HashMap<String, String>();
        Iterator<String> keyIt = listMap.keySet().iterator();
        while (keyIt.hasNext()){
            String keyEntry = keyIt.next();
            String valueEntry = listMap.get(keyEntry);
            String resultKey = keyEntry.substring(0, keyEntry.lastIndexOf("-"));
            if(collresult.containsKey(resultKey)){
                collresult.put(resultKey,collresult.get(resultKey)+ "/" + keyEntry.substring(keyEntry.lastIndexOf("-")+1));
            } else {
                collresult.put(resultKey, valueEntry.substring(0, valueEntry.length() -1));
            }

        }
        keyIt = collresult.keySet().iterator();
        while (keyIt.hasNext()){
            String keyEntry = keyIt.next();
            String valueEntry = collresult.get(keyEntry);
            collresult.put(keyEntry, valueEntry+"元");
        }
        return collresult;
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

        List<Map<String, String>> emptyList = new ArrayList<Map<String, String>>();
        List<Map<String, String>> settleList = rechargeRemoteInterface.statisticRecharge(selectedShortcode, selectedChannel, beginDate, endDate);
        if(selectedChannel.equalsIgnoreCase("all_multi")){
            List<Map<String, String>> adaList = activeDeviceRemoteInterface.statisticActiveDevice(selectedShortcode, beginDate, endDate);
            for(Map<String, String> adaEntry:adaList){
                boolean empty = true;
                for(Map<String, String> entry:settleList){
                    if(entry.get("statisticTarget").equalsIgnoreCase(adaEntry.get("statisticTarget"))){
                        empty = false;
                        entry.put("ac_count", adaEntry.get("s_count"));
                    }
                }
                if(empty){
                    adaEntry.put("ac_count", adaEntry.get("s_count"));
                    adaEntry.put("s_count", "0");
                    adaEntry.put("s_sum", "0");
                    adaEntry.put("s1_count", "0");
                    adaEntry.put("s1_sum", "0");
                    adaEntry.put("s4_count", "0");
                    adaEntry.put("s4_sum", "0");
                    adaEntry.put("s6_count", "0");
                    adaEntry.put("s6_sum", "0");
                    emptyList.add(adaEntry);
                }
            }
            settleList.addAll(emptyList);
        }else {
            for(Map<String,String> entry:settleList){
                entry.put("ac_count","不支持分通道统计");
            }
        }
        return settleList;
    }

    @Override
    public String suppleOrder(String id) {
        if(StringUtils.isEmpty(id)){
            return "失败，参数无效！";
        }
        return rechargeRemoteInterface.suppleOrder(id);
    }

    @Override
    public List<Map<String, String>> getActiveDeviceAccount(String selectedCp, String beginDate, String endDate) {
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if(endDate == null){
            endDate = simpleDateFormat.format(nowDate);
        }

        if(beginDate == null){
            nowDate.setTime(nowDate.getTime()-60*60*24*30*1000);
            beginDate = simpleDateFormat.format(nowDate);
        }
        return activeDeviceRemoteInterface.statisticActiveDevice(selectedCp, beginDate, endDate);
    }

}
