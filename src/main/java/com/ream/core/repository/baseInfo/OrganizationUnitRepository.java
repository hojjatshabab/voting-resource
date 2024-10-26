package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.repository.baseInfo.extra.OrganizationUnitExtraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrganizationUnitRepository extends JpaRepository<OrganizationUnit, UUID>, OrganizationUnitExtraRepository {

    OrganizationUnit findByParentIdIsNull();

    Optional<OrganizationUnit> findByName(String name);

    Optional<OrganizationUnit> findByCodePath(String codePath);

    Optional<List<OrganizationUnit>> findByParentIdOrderByCodePathDesc(UUID parentId);



    Optional<List<OrganizationUnit>> findByCode(String code);

    Optional<List<OrganizationUnit>> findByNameContains(String name);

    @Query(value = "SELECT o FROM OrganizationUnit o WHERE o.codePath like :codePath order by o.codePath asc ")
    Optional<List<OrganizationUnit>> findAllChildrenByCodePath(@Param("codePath") String codePath);

    @Override
    Page<OrganizationUnit> findAll(Pageable pageable);
}
