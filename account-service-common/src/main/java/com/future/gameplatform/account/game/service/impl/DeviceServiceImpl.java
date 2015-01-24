package com.future.gameplatform.account.game.service.impl;

import com.future.gameplatform.account.game.dao.DeviceDao;
import com.future.gameplatform.account.game.entity.Device;
import com.future.gameplatform.account.game.service.DeviceService;
import com.future.gameplatform.common.id.IdFactory;
import org.springframework.util.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-31
 * Time: 下午1:23
 * To change this template use File | Settings | File Templates.
 */
public class DeviceServiceImpl implements DeviceService {

    private DeviceDao deviceDao;

    public void setDeviceDao(DeviceDao deviceDao) {
        this.deviceDao = deviceDao;
    }

    @Override
    public Device insertDevice(Device device) {
        if(StringUtils.hasText(device.getDid()) && StringUtils.hasText(device.getMobile())){
            Device oldDevice = deviceDao.getByMobileDid(device.getMobile(), device.getDid());
            if(oldDevice != null){
                device.setId(oldDevice.getId());
            }else {
                device.setId(IdFactory.generateId());
            }
            return deviceDao.insert(device);
        }
        return null;
    }


}
