package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Resource;

import java.util.List;

public class ResourceDaoImpl extends BasicDaoImpl implements ResourceDao {


    public ResourceDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public Resource createResource(Resource resource) {
        datastore.save(resource);
        return resource;
    }

    @Override
    public Resource updateResource(Resource resource) {
        datastore.save(resource);
        return resource;
    }

    @Override
    public void deleteResource(String resourceId) {
        datastore.delete(Resource.class, resourceId);
    }

    @Override
    public Resource findOne(String resourceId) {
        return datastore.get(Resource.class, resourceId);
    }

    @Override
    public List<Resource> findAll() {
        return datastore.find(Resource.class).asList();
    }
}
