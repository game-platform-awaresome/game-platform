package com.future.gameplatform.account.game.dao.impl;

import com.future.gameplatform.account.game.dao.DeviceDao;
import com.future.gameplatform.account.game.entity.Device;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: JohnKee
 * Date: 14-10-31
 * Time: 下午1:16
 * To change this template use File | Settings | File Templates.
 */
public class DeviceDaoImpl extends BasicDaoImpl implements DeviceDao {

    public DeviceDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public Device insert(Device device) {
        datastore.save(device);
        return device;
    }

    @Override
    public Device getById(String id) {
        return datastore.get(Device.class, id);
    }

    @Override
    public void deleteById(String id) {
        datastore.delete(Device.class, id);
    }

    @Override
    public List<Device> listAll() {
        return datastore.find(Device.class).asList();
    }

    @Override
    public Device getByMobileDid(String mobile, String did) {
        return datastore.find(Device.class).filter("mobile", mobile).filter("did", did).get();
    }
}
