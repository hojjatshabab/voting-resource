package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.City;
import com.ream.core.domain.baseInfo.Data;
import com.ream.core.repository.baseInfo.extra.CityExtraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CityRepository extends JpaRepository<City, UUID>, CityExtraRepository {
    List<City> findByCommonBaseDataProvince(Data commonBaseData);

    Optional<List<City>> findByNameContains(String name);

    City findByCode(String code);
}
