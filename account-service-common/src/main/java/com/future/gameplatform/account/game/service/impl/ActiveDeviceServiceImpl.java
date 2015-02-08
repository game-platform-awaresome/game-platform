package com.future.gameplatform.account.game.service.impl;

import com.future.gameplatform.account.game.dao.DeviceDao;
import com.future.gameplatform.account.game.entity.DeviceActive;
import com.future.gameplatform.common.id.IdFactory;
import com.future.gameplatform.common.service.ActiveDeviceRemoteInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by johnKee on 2015/1/24.
 */
public class ActiveDeviceServiceImpl implements ActiveDeviceRemoteInterface {

    private Logger logger = LoggerFactory.getLogger(ActiveDeviceServiceImpl.class);

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
        logger.debug("statistic active device");
        List<DeviceActive> listActive= deviceDao.listActiveForStatistic(selectedCp, beginDate, endDate);
        if(listActive == null || listActive.size() < 1){
            return Collections.EMPTY_LIST;
        }
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();

        Map<String,Set<String>> mapSet = new HashMap<String, Set<String>>();

        Map<String, Integer> mapIndex = new HashMap<String, Integer>();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date bDate = simpleDateFormat.parse(beginDate);
            Date eDate = simpleDateFormat.parse(endDate);
            while (bDate.compareTo(eDate) < 0){
                Map<String, String>  map = new HashMap<String, String>();
                map.put("statisticTarget", simpleDateFormat.format(bDate));
                map.put("s_count","0");
                result.add(map);
                mapSet.put(simpleDateFormat.format(bDate), new HashSet<String>());
                mapIndex.put(simpleDateFormat.format(bDate), result.size() - 1);
                bDate.setTime(bDate.getTime() + 1000*60*60*24);
            }
        } catch (ParseException e) {
            logger.error("date format error", e);
        }


        /**
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
                    Set<String> set = new HashSet<String>();
                    set.add(deviceActive.getDid());
                    mapSet.put(deviceActive.getShortcode(), set);
                } else {
                    mapEntry = result.get(mapIndex.get(deviceActive.getShortcode()));
                    Set<String> setValue = mapSet.get(deviceActive.getShortcode());
                    if(!setValue.contains(deviceActive.getDid())){
                        mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                        setValue.add(deviceActive.getDid());
                    }
                }
            }
        }else {
         **/
            Iterator<DeviceActive> srIt = listActive.iterator();

            while (srIt.hasNext()) {
                DeviceActive deviceActive = srIt.next();
                Map<String, String> mapEntry = null;

                mapEntry = result.get(mapIndex.get(simpleDateFormat.format(deviceActive.getCreatedDate())));
                Set<String> setValue = mapSet.get(simpleDateFormat.format(deviceActive.getCreatedDate()));
                if(!setValue.contains(deviceActive.getDid())){
                    mapEntry.put("s_count", String.valueOf(1 + Integer.parseInt(mapEntry.get("s_count"))));
                    setValue.add(deviceActive.getDid());
                }
        }
        return result;
    }
}
