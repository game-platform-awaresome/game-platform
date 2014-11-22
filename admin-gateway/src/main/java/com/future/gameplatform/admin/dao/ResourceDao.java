package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Resource;

import java.util.List;

/**
 * <p>Resource: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface ResourceDao {

    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(String resourceId);

    Resource findOne(String resourceId);
    List<Resource> findAll();

}
