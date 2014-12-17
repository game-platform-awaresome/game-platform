package com.future.gameplatform.common.service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-17
 * Time: 下午8:37
 * To change this template use File | Settings | File Templates.
 */
public interface RechargeRemoteInterface {

    public List<Map<String, String>> statisticRecharge(String selectedShortcode, String selectedChannel, String beginDate, String endDate);

    public List<Map<String, String>> queryRecharge(String shortcode, String mobile, String orderno, String id, String begindate, String enddate);

}
