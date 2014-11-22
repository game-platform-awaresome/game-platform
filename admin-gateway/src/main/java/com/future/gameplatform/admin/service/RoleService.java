package com.future.gameplatform.admin.service;


import com.future.gameplatform.admin.entity.Role;

import java.util.List;
import java.util.Set;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface RoleService {


    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(String roleId);

    public Role findOne(String roleId);
    public List<Role> findAll();

    /**
     * 根据角色编号得到角色标识符列表
     * @param roleIds
     * @return
     */
    Set<String> findRoles(String... roleIds);

    /**
     * 根据角色编号得到权限字符串列表
     * @param roleIds
     * @return
     */
    Set<String> findPermissions(String[] roleIds);
}
