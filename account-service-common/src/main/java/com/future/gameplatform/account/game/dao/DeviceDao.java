package com.future.gameplatform.account.game.dao;

import com.future.gameplatform.account.game.entity.Device;
import com.future.gameplatform.account.game.entity.DeviceActive;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-31
 * Time: 下午1:13
 * To change this template use File | Settings | File Templates.
 */
public interface DeviceDao {

    Device insert(Device device);

    Device getById(String id);

    void deleteById(String id);

    List<Device> listAll();

    Device getByMobileDid(String mobile, String did);

    Boolean insertActive(DeviceActive da);

    List<DeviceActive> listActiveForStatistic(String selectedCp, String beginDate, String endDate);
}
