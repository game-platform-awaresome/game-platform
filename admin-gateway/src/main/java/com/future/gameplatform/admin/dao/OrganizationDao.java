package com.future.gameplatform.admin.dao;


import com.future.gameplatform.admin.entity.Organization;

import java.util.List;

/**
 * <p>Organization: Zhang Kaitao
 * <p>Date: 14-1-28
 * <p>Version: 1.0
 */
public interface OrganizationDao {

    public Organization createOrganization(Organization organization);

    public Organization updateOrganization(Organization organization);

    public void deleteOrganization(String organizationId);

    Organization findOne(String organizationId);

    List<Organization> findAll();

    List<Organization> findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
