package com.future.gameplatform.admin.service;


import com.future.gameplatform.admin.entity.Resource;

import java.util.List;
import java.util.Set;


public interface ResourceService {


    public Resource createResource(Resource resource);
    public Resource updateResource(Resource resource);
    public void deleteResource(String resourceId);

    Resource findOne(String resourceId);
    List<Resource> findAll();

    /**
     * 得到资源对应的权限字符串
     * @param resourceIds
     * @return
     */
    Set<String> findPermissions(Set<String> resourceIds);

    /**
     * 根据用户权限得到菜单
     * @param permissions
     * @return
     */
    List<Resource> findMenus(Set<String> permissions);
}
