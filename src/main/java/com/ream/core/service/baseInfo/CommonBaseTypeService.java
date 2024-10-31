package com.ream.core.service.baseInfo;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.CommonBaseTypeDto;


import java.util.List;
import java.util.Optional;

public interface CommonBaseTypeService {
    CommonBaseTypeDto save(CommonBaseTypeDto commonBaseTypeDto);

    CommonBaseTypeDto update(CommonBaseTypeDto commonBaseTypeDto);

    PageResponse<CommonBaseTypeDto> findAll(PageRequest<CommonBaseTypeDto> model);

    PageResponse<CommonBaseTypeDto> findByClassNameOrTitle(String className, String title, PageRequest<CommonBaseTypeDto> model);

    Optional<CommonBaseTypeDto> findById(Long id);

    Optional<List<CommonBaseTypeDto>> findByTitleContains(String name);

    Optional<CommonBaseTypeDto> findByClassName(String className);

    Boolean deleteById(Long id);
}
