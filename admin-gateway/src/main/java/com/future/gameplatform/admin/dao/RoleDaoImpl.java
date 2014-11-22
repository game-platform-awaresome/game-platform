package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Role;

import java.util.List;

public class RoleDaoImpl extends BasicDaoImpl implements RoleDao {

    public RoleDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public Role createRole(Role role) {
        datastore.save(role);
        return role;
    }

    @Override
    public Role updateRole(Role role) {
        datastore.save(role);
        return role;
    }

    @Override
    public void deleteRole(String roleId) {
        datastore.delete(Role.class, roleId);
    }

    @Override
    public Role findOne(String roleId) {
        return datastore.get(Role.class, roleId);
    }

    @Override
    public List<Role> findAll() {
        return datastore.find(Role.class).asList();
    }
}
