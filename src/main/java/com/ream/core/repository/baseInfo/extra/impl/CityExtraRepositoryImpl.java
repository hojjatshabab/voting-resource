package com.ream.core.repository.baseInfo.extra.impl;

import com.ream.core.domain.baseInfo.City;
import com.ream.core.repository.baseInfo.extra.CityExtraRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@RequiredArgsConstructor
public class CityExtraRepositoryImpl implements CityExtraRepository {
    @Autowired
    EntityManager entityManager;


    @Override
    public List<City> findAllByProvinceAndName(Long province, String name, Integer pageSize, Integer currentPage) {

        String querySt = "select c from City c  where 1=1  ";
        if (province != null ) {
            querySt += " and  c.province.id =:province ";
        }
        if (name != null || !name.isEmpty() ||  !name.isBlank()  ) {
            querySt += " and c.name like :name ";
        }


        Query query = entityManager.createQuery(querySt);
        if (province != null) {
            query.setParameter("province", province);
        }
        if (name != null) {
            query.setParameter("name", "%" + name + "%");
        }
        query.setFirstResult((currentPage - 1) * pageSize);
        query.setMaxResults(pageSize);
        return (List<City>)query.getResultList();
    }
}
