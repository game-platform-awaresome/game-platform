package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Organization;

import java.util.List;

public class OrganizationDaoImpl extends BasicDaoImpl implements OrganizationDao {


    public OrganizationDaoImpl(String mongoDomain, String dbName) {
        super(mongoDomain);
        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    @Override
    public Organization createOrganization(Organization organization) {
        datastore.save(organization);
        return organization;
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        datastore.save(organization);
        return organization;
    }

    @Override
    public void deleteOrganization(String organizationId) {
        datastore.delete(Organization.class, organizationId);
    }

    @Override
    public Organization findOne(String organizationId) {
        return datastore.get(Organization.class, organizationId);
    }

    @Override
    public List<Organization> findAll() {
        return datastore.find(Organization.class).asList();
    }

    @Override
    public List<Organization> findAllWithExclude(Organization excludeOraganization) {
        return datastore.find(Organization.class).field("_id").notEqual(excludeOraganization.getId()).asList();
    }

    @Override
    public void move(Organization source, Organization target) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
