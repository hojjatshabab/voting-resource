package com.ream.core.service.baseInfo;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.CommonBaseDataDto;


import java.util.List;
import java.util.Optional;

public interface CommonBaseDataService {
     CommonBaseDataDto save(CommonBaseDataDto data);

    CommonBaseDataDto update(CommonBaseDataDto data);

    PageResponse<CommonBaseDataDto> findAll(PageRequest<CommonBaseDataDto> model);

    PageResponse<CommonBaseDataDto> findByClassName(String className, PageRequest<CommonBaseDataDto> model);

    Optional<List<CommonBaseDataDto>> findAllByClassName(String className);

    PageResponse<CommonBaseDataDto> findByValueAndType(String value, String className, PageRequest<CommonBaseDataDto> model);

    Optional<CommonBaseDataDto> findById(Long id);

    Boolean deleteById(Long id);

    List<CommonBaseDataDto> search(String pattern);
}
