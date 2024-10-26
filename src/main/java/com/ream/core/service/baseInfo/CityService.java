package com.ream.core.service.baseInfo;



import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.CityDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CityService {

    CityDto save(CityDto cityDto);
    CityDto update(CityDto cityDto);
    PageResponse<CityDto> findAll(Long province, String name, PageRequest<CityDto> model);
    Optional<CityDto> findById(UUID id);
    CityDto findByCode(String code);

    Optional<List<CityDto>> findByNameContains(String name);
    List<CityDto> findByCommonBaseDataProvince(Long provinceId);
    Boolean deleteById(UUID id);
}
