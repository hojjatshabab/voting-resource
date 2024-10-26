package com.ream.core.service.baseInfo;

import com.ream.core.domain.baseInfo.Data;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.DataDto;


import java.util.List;
import java.util.Optional;

public interface CommonBaseDataService {
     DataDto save(DataDto data);

    DataDto update( DataDto data);

    PageResponse<DataDto> findAll(PageRequest<DataDto> model);

    PageResponse<DataDto> findByClassName(String className, PageRequest<DataDto> model);

    Optional<List<DataDto>> findAllByClassName(String className);

    PageResponse< DataDto> findByValueAndType(String value, String className, PageRequest< DataDto> model);

    Optional< DataDto> findById(Long id);

    Boolean deleteById(Long id);

    List< DataDto> search(String pattern);
}
