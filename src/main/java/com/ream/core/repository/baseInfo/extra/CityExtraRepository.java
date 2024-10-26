package com.ream.core.repository.baseInfo.extra;



import com.ream.core.domain.baseInfo.City;

import java.util.List;

public interface CityExtraRepository {

    List<City> findAllByProvinceAndName(Long province, String name, Integer pageSize, Integer currentPage);


}
