package com.future.gameplatform.recharge.common.service.impl;

import com.future.gameplatform.common.service.RechargeRemoteInterface;
import com.future.gameplatform.recharge.common.dao.SmsRechargeDao;
import com.future.gameplatform.recharge.common.entity.SmsRecharge;
import com.future.gameplatform.recharge.common.util.RechargeStateEnum;
import com.future.gameplatform.recharge.common.util.ServiceResult;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-17
 * Time: 下午8:40
 * To change this template use File | Settings | File Templates.
 */
public class RechargeRemoteServiceImpl implements RechargeRemoteInterface {

    private SmsRechargeDao smsRechargeDao;

    public void setSmsRechargeDao(SmsRechargeDao smsRechargeDao) {
        this.smsRechargeDao = smsRechargeDao;
    }

    private NoticeCpHelper noticeCpHelper;

    public void setNoticeCpHelper(NoticeCpHelper noticeCpHelper) {
        this.noticeCpHelper = noticeCpHelper;
    }

    @Override
    public List<Map<String, String>> statisticRecharge(String selectedShortcode, String selectedChannel, String beginDate, String endDate) {
        List<SmsRecharge> listRecharge = smsRechargeDao.listForStatistic(selectedShortcode, selectedChannel, beginDate, endDate);
        if(listRecharge == null || listRecharge.size() < 1){
            return Collections.EMPTY_LIST;
        }
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, Integer> mapIndex = new HashMap<String, Integer>();
        if(selectedShortcode.equals("all_multi")){
            Iterator<SmsRecharge> srIt = listRecharge.iterator();
            while (srIt.hasNext()){
                SmsRecharge smsRecharge = srIt.next();
                Map<String, String> mapEntry = null;
                if(mapIndex.get(smsRecharge.getShortcode()) == null){
                    mapEntry = new HashMap<String, String>();
                    mapEntry.put("s_count", "0");
                    mapEntry.put("s_sum", "0");
                    mapEntry.put("s1_count", "0");
                    mapEntry.put("s1_sum", "0");
                    mapEntry.put("s4_count", "0");
                    mapEntry.put("s4_sum", "0");
                    mapEntry.put("s6_count", "0");
                    mapEntry.put("s6_sum", "0");
                    mapEntry.put("statisticTarget", smsRecharge.getShortcode());
                    result.add(mapEntry);
                    mapIndex.put(smsRecharge.getShortcode(), result.size() - 1);
                } else {
                    mapEntry = result.get(mapIndex.get(smsRecharge.getShortcode()));
                }
                switch (smsRecharge.getState()){
                    case 1:
                    case 2:
                    case 3:
                        mapEntry.put("s1_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s1_count"))));
                        mapEntry.put("s1_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s1_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 4:
                    case 5:
                        mapEntry.put("s4_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s4_count"))));
                        mapEntry.put("s4_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s4_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 6:
                        mapEntry.put("s6_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s6_count"))));
                        mapEntry.put("s6_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s6_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                }
            }
        }else if(selectedChannel.equals("all_multi")){
            Iterator<SmsRecharge> srIt = listRecharge.iterator();
            while (srIt.hasNext()){
                SmsRecharge smsRecharge = srIt.next();
                Map<String, String> mapEntry = null;
                if(mapIndex.get(smsRecharge.getChannel()) == null){
                    mapEntry = new HashMap<String, String>();
                    mapEntry.put("s_count", "0");
                    mapEntry.put("s_sum", "0");
                    mapEntry.put("s1_count", "0");
                    mapEntry.put("s1_sum", "0");
                    mapEntry.put("s4_count", "0");
                    mapEntry.put("s4_sum", "0");
                    mapEntry.put("s6_count", "0");
                    mapEntry.put("s6_sum", "0");
                    mapEntry.put("statisticTarget", smsRecharge.getChannel());
                    result.add(mapEntry);
                    mapIndex.put(smsRecharge.getChannel(),result.size()-1);
                } else {
                    mapEntry = result.get(mapIndex.get(smsRecharge.getChannel()));
                }
                switch (smsRecharge.getState()){
                    case 1:
                    case 2:
                    case 3:
                        mapEntry.put("s1_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s1_count"))));
                        mapEntry.put("s1_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s1_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 4:
                    case 5:
                        mapEntry.put("s4_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s4_count"))));
                        mapEntry.put("s4_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s4_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 6:
                        mapEntry.put("s6_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s6_count"))));
                        mapEntry.put("s6_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s6_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                }
            }
        }else {
            Iterator<SmsRecharge> srIt = listRecharge.iterator();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (srIt.hasNext()){
                SmsRecharge smsRecharge = srIt.next();
                Map<String, String> mapEntry = null;
                if(mapIndex.get(simpleDateFormat.format(smsRecharge.getCreatedDate())) == null){
                    mapEntry = new HashMap<String, String>();
                    mapEntry.put("s_count", "0");
                    mapEntry.put("s_sum", "0");
                    mapEntry.put("s1_count", "0");
                    mapEntry.put("s1_sum", "0");
                    mapEntry.put("s4_count", "0");
                    mapEntry.put("s4_sum", "0");
                    mapEntry.put("s6_count", "0");
                    mapEntry.put("s6_sum", "0");
                    mapEntry.put("statisticTarget", simpleDateFormat.format(smsRecharge.getCreatedDate()));
                    result.add(mapEntry);
                    mapIndex.put(simpleDateFormat.format(smsRecharge.getCreatedDate()),result.size()-1);
                } else {
                    mapEntry = result.get(mapIndex.get(simpleDateFormat.format(smsRecharge.getCreatedDate())));
                }
                switch (smsRecharge.getState()){
                    case 1:
                    case 2:
                    case 3:
                        mapEntry.put("s1_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s1_count"))));
                        mapEntry.put("s1_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s1_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 4:
                    case 5:
                        mapEntry.put("s4_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s4_count"))));
                        mapEntry.put("s4_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s4_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                    case 6:
                        mapEntry.put("s6_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s6_count"))));
                        mapEntry.put("s6_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s6_sum")))));

                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        mapEntry.put("s_sum", String.valueOf(new BigDecimal(smsRecharge.getFee()).add(new BigDecimal(mapEntry.get("s_sum")))));
                        break;
                }
            }
        }
        return result;
    }

    @Override
    public List<Map<String, String>> queryRecharge(String shortcode, String mobile, String orderno, String id, String begindate, String enddate) {
        List<SmsRecharge> listRecharge = smsRechargeDao.listForQuery(shortcode, mobile, orderno, id, begindate, enddate);
        if(listRecharge == null || listRecharge.size() < 1){
            return Collections.EMPTY_LIST;
        } else if(listRecharge.size() > 100) {
            listRecharge = listRecharge.subList(0, 99);
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Iterator<SmsRecharge> srIt = listRecharge.iterator();
        while (srIt.hasNext()){
            SmsRecharge smsRecharge = srIt.next();
            Map<String, String> map = new HashMap<String, String>();
            map.put("shortcode", smsRecharge.getShortcode());
            map.put("channel", smsRecharge.getChannel());
            map.put("orderno", smsRecharge.getOrderno());
            map.put("id", smsRecharge.getId());
            map.put("createdDate", simpleDateFormat.format(smsRecharge.getCreatedDate()));
            map.put("fee", smsRecharge.getFee());
            map.put("mobile", smsRecharge.getMobile());
            map.put("state", String.valueOf(smsRecharge.getState()));
            result.add(map);
        }
        return result;
    }

    @Override
    public String suppleOrder(String id) {
        SmsRecharge smsRecharge = smsRechargeDao.getById(id);
        if(smsRecharge == null){
            return "失败，流水号不存在有效订单！";
        }
        if(smsRecharge.getState()<4){
            return "失败，计费失败的订单不允许补单！" ;
        }
        ServiceResult<String> noticeCpResult = noticeCpHelper.noticeCp(smsRecharge.getShortcode(),smsRecharge.getOrderno(),id,smsRecharge.getFee());
        if(noticeCpResult.isSuccess()){
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_OK.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            smsRechargeDao.save(smsRecharge);
            return "成功，补单成功！";
        }else {
            smsRecharge.setState(RechargeStateEnum.NOTICED_CP_FAILED.getIndex());
            smsRecharge.setNoticeCpDate(new Date());
            smsRechargeDao.save(smsRecharge);
            return "结果未知，已经通知CP补单，但CP未反馈成功！";
        }
    }
}
