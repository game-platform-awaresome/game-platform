package com.future.gameplatform.admin.service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-14
 * Time: 下午8:32
 * To change this template use File | Settings | File Templates.
 */
public interface RechargeQueryService {
    List<Map<String, String>> queryOrder(String shortcode, String mobile, String orderno, String id, String begindate, String enddate);
}
