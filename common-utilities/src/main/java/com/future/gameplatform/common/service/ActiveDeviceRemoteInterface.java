package com.future.gameplatform.common.service;

import java.util.List;
import java.util.Map;

/**
 * Created by johnKee on 2015/1/24.
 */
public interface ActiveDeviceRemoteInterface {

    Boolean activeDeviceRecord(String shortCode, String did, String mobile, String dType, String brand, String os, String version);

    List<Map<String,String>> statisticActiveDevice(String selectedCp, String beginDate, String endDate);
}
