package com.ream.core.repository.baseInfo.extra.impl;

import com.ream.core.domain.baseInfo.Data;
import com.ream.core.repository.baseInfo.extra.CommonBaseDataExtraRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public class CommonBaseDataExtraRepositoryImpl implements CommonBaseDataExtraRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Data> search(String pattern) {
        String query = " select c from Data c where c.description = :pattern ";

        return entityManager.createQuery(query).setParameter("pattern",pattern).getResultList();
    }
}
