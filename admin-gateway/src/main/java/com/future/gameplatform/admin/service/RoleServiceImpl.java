package com.future.gameplatform.admin.service;


import com.future.gameplatform.admin.dao.RoleDao;
import com.future.gameplatform.admin.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private ResourceService resourceService;

    public Role createRole(Role role) {
        return roleDao.createRole(role);
    }

    public Role updateRole(Role role) {
        return roleDao.updateRole(role);
    }

    public void deleteRole(String roleId) {
        roleDao.deleteRole(roleId);
    }

    @Override
    public Role findOne(String roleId) {
        return roleDao.findOne(roleId);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

    @Override
    public Set<String> findRoles(String... roleIds) {
        Set<String> roles = new HashSet<String>();
        for(String roleId : roleIds) {
            Role role = findOne(roleId);
            if(role != null) {
                roles.add(role.getRole());
            }
        }
        return roles;
    }

    @Override
    public Set<String> findPermissions(String[] roleIds) {
        Set<String> resourceIds = new HashSet<String>();
        for(String roleId : roleIds) {
            Role role = findOne(roleId);
            if(role != null) {
                resourceIds.addAll(role.getResourceIds());
            }
        }
        return resourceService.findPermissions(resourceIds);
    }
}
