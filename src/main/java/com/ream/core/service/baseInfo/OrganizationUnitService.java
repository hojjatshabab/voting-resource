package com.ream.core.service.baseInfo;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationUnitService {

    OrganizationUnitDto save(OrganizationUnitDto organizationUnitDto);

    OrganizationUnitDto getCurrentOrganizationUnit();

    OrganizationUnitDto update(OrganizationUnitDto organizationUnitDto);

    PageResponse<OrganizationUnitDto> findAll(PageRequest<OrganizationUnitDto> model);

    Optional<OrganizationUnitDto> findById(UUID id);

    Optional<OrganizationUnitDto> findByName(String name);

    Optional<List<OrganizationUnitDto>> findByCode(String code);

    Optional<List<OrganizationUnitDto>> findAllChildrenById(UUID id);

    Optional<List<OrganizationUnitDto>> findByNameContains(String name);

    Optional<List<OrganizationUnitDto>> findChildrenById(UUID id);
    Optional<List<OrganizationUnitDto>> findAllParentByOrgId(UUID id);

    Optional<OrganizationUnitDto> findParentByOrgId(UUID id);


    Optional<List<OrganizationUnitDto>> findChildrenByCodePath(String code);

    Optional<List<OrganizationUnitDto>> findAllChildrenByCodePath(String name);

    Optional<List<OrganizationUnitDto>> changeNodeByParentId(String parentId, OrganizationUnitDto organizationUnitDto);

    OrganizationUnitDto findByParentIdIsNull();

    Boolean deleteById(UUID id);

    String generateCompleteName(OrganizationUnitDto organizationUnitDto);

    String generateCodePath(OrganizationUnitDto organizationUnitDto);

    String generateCodePathByParentId(OrganizationUnitDto organizationUnitDto);

}
