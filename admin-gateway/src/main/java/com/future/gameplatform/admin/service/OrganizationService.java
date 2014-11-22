package com.future.gameplatform.admin.service;

import com.future.gameplatform.admin.entity.Organization;

import java.util.List;


public interface OrganizationService {


    public Organization createOrganization(Organization organization);

    public Organization updateOrganization(Organization organization);

    public void deleteOrganization(String organizationId);

    Organization findOne(String organizationId);

    List<Organization> findAll();

    Object findAllWithExclude(Organization excludeOraganization);

    void move(Organization source, Organization target);
}
