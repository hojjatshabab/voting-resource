package com.ream.core.repository.baseInfo.extra.impl;

import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.repository.baseInfo.extra.OrganizationUnitExtraRepository;
import lombok.RequiredArgsConstructor;


import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;


@RequiredArgsConstructor
public class OrganizationUnitExtraRepositoryImpl implements OrganizationUnitExtraRepository {
    @Autowired
   EntityManager entityManager;


    @Override
    public String findUniqueCodePathByParentCodePath(String parentCodePath) {
        if (parentCodePath.isEmpty()) return null;
        String querySt = " select o from OrganizationUnit o where o.codePath like :value order by o.codePath desc ";
        Query query = entityManager.createQuery(querySt);
        query.setParameter("value", parentCodePath + "%");
        query.setMaxResults(1);
        List<OrganizationUnit> units = query.getResultList();
        String tempCodePath = units.get(0).getCodePath();
        if (tempCodePath.trim().length() == parentCodePath.trim().length()) return tempCodePath.concat("001");
        else {
            Integer intCodePath = Integer.valueOf(tempCodePath.trim()) + 1;
            return "00".concat(intCodePath.toString());
        }
    }

    @Override
    public Optional<List<OrganizationUnit>> findByParentIdOrderByCodePathAsc(UUID parentId) {
        if (Objects.isNull(parentId)) return Optional.ofNullable(new ArrayList<>());
        String querySt = " select o from OrganizationUnit o where o.parent.id =:parentId order by o.codePath asc ";
        Query query = entityManager.createQuery(querySt);
        query.setParameter("parentId", parentId);
        return Optional.ofNullable((List<OrganizationUnit>)query.getResultList());
    }


}
