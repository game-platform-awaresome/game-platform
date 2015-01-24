package com.future.gameplatform.account.game.service.impl;

import com.future.gameplatform.account.game.dao.DeviceDao;
import com.future.gameplatform.account.game.entity.DeviceActive;
import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.common.service.ActiveDeviceRemoteInterface;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by johnKee on 2015/1/24.
 */
public class ActiveDeviceServiceImpl implements ActiveDeviceRemoteInterface {

    private DeviceDao deviceDao;

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Boolean activeDeviceRecord(String shortCode, String did, String mobile, String dType, String brand, String os, String version) {
        if(StringUtils.hasText(shortCode) && StringUtils.hasText(did)){
            DeviceActive da = new DeviceActive();
            da.setId(IdFactory.generateId());
            da.setShortcode(shortCode);
            da.setDid(did);
            da.setDtype(dType);
            da.setBrand(brand);
            da.setMobile(mobile);
            da.setOs(os);
            da.setVersion(version);
            da.setCreatedDate(new Date());
            return deviceDao.insertActive(da);
        }
        return false;
    }

    @Override
    public List<Map<String, String>> statisticActiveDevice(String selectedCp, String beginDate, String endDate) {

        List<DeviceActive> listActive= deviceDao.listActiveForStatistic(selectedCp, beginDate, endDate);
        if(listActive == null || listActive.size() < 1){
            return Collections.EMPTY_LIST;
        }
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, Integer> mapIndex = new HashMap<String, Integer>();
        if(selectedCp.equals("all_multi")) {
            Iterator<DeviceActive> srIt = listActive.iterator();
            while (srIt.hasNext()) {
                DeviceActive deviceActive = srIt.next();
                Map<String, String> mapEntry = null;
                if (mapIndex.get(deviceActive.getShortcode()) == null) {
                    mapEntry = new HashMap<String, String>();
                    mapEntry.put("s_count", "0");
                    mapEntry.put("statisticTarget", deviceActive.getShortcode());
                    result.add(mapEntry);
                    mapIndex.put(deviceActive.getShortcode(), result.size() - 1);
                } else {
                    mapEntry = result.get(mapIndex.get(deviceActive.getShortcode()));
                }
                mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
            }
        }else {
            Iterator<DeviceActive> srIt = listActive.iterator();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            while (srIt.hasNext()) {
                DeviceActive deviceActive = srIt.next();
                Map<String, String> mapEntry = null;
                if (mapIndex.get(simpleDateFormat.format(deviceActive.getCreatedDate())) == null) {
                    mapEntry = new HashMap<String, String>();
                    mapEntry.put("s_count", "0");
                    mapEntry.put("statisticTarget", simpleDateFormat.format(deviceActive.getCreatedDate()));
                    result.add(mapEntry);
                    mapIndex.put(simpleDateFormat.format(deviceActive.getCreatedDate()), result.size() - 1);
                } else {
                    mapEntry = result.get(mapIndex.get(simpleDateFormat.format(deviceActive.getCreatedDate())));
                }
                mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
            }
        }
        return result;
    }
}
