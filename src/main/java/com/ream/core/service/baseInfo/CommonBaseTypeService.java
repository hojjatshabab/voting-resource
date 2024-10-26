package com.ream.core.service.baseInfo;

import com.ream.core.domain.baseInfo.Type;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.TypeDto;


import java.util.List;
import java.util.Optional;

public interface CommonBaseTypeService {
    TypeDto save(TypeDto commonBaseTypeDto);

    TypeDto update(TypeDto commonBaseTypeDto);

    PageResponse<TypeDto> findAll(PageRequest<TypeDto> model);

    PageResponse<TypeDto> findByClassNameOrTitle(String className, String title, PageRequest<TypeDto> model);

    Optional<TypeDto> findById(Long id);

    Optional<List<TypeDto>> findByTitleContains(String name);

    Optional<TypeDto> findByClassName(String className);

    Boolean deleteById(Long id);
}
