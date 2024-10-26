package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.MenuDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuService {


    MenuDto save(MenuDto menuDto);

    MenuDto update(MenuDto menuDto);

    PageResponse<MenuDto> findAll(PageRequest<MenuDto> model);

    Optional<MenuDto> findById(UUID id);

    List<MenuDto> findAll();

    List<String> getMenuKeyByMenuId(UUID menuId);

    Boolean deleteById(UUID id);


}
