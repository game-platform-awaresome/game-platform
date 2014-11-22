package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Role;

import java.util.List;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface RoleDao {

    public Role createRole(Role role);
    public Role updateRole(Role role);
    public void deleteRole(String roleId);

    public Role findOne(String roleId);
    public List<Role> findAll();
}
