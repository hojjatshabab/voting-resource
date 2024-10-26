package com.ream.core.repository.baseInfo.extra;



import com.ream.core.domain.baseInfo.OrganizationUnit;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrganizationUnitExtraRepository {

    String findUniqueCodePathByParentCodePath(String parentCodePath);




    Optional<List<OrganizationUnit>> findByParentIdOrderByCodePathAsc(UUID parentId);






}
