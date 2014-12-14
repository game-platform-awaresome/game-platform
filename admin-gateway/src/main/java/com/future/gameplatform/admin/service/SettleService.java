package com.future.gameplatform.admin.service;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-12-14
 * Time: 下午7:36
 * To change this template use File | Settings | File Templates.
 */
public interface SettleService {

    Map<String, String> getCPList();

    Map<String, String> getTemplateChannelList();

    List<Map<String, String>> getSettle(String selectedShortcode, String selectedChannel, String beginDate, String endDate);
}
