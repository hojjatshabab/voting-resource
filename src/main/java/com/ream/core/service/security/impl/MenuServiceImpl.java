package com.ream.core.service.security.impl;

import com.ream.core.domain.security.Menu;
import com.ream.core.repository.security.MenuRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.MenuService;
import com.ream.core.service.security.dto.MenuDto;
import com.ream.core.service.security.mapper.MenuDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {


    @Autowired
    MenuRepository menuRepository;


    @Autowired
    MenuDtoMapper menuDtoMapper;


    @Override
    public MenuDto save(MenuDto menuDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        return menuDtoMapper.toDto(menuRepository.save(menuDtoMapper.toEntity(menuDto)));
    }

    @Override
    public MenuDto update(MenuDto menuDto) {
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return menuDtoMapper.toDto(menuRepository.save(menuDtoMapper.toEntity(menuDto)));

    }

    @Override
    public PageResponse<MenuDto> findAll(PageRequest<MenuDto> model) {
        List<MenuDto> result = menuRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(menuDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = menuRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<MenuDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isPresent()) {
            return Optional.ofNullable(menuDtoMapper.toDto(optionalMenu.get()));
        }
        return Optional.empty();
    }

    @Override
    public List<MenuDto> findAll() {
        List<Menu> list = menuRepository.findAll();
        if (list.size() > 0) {
            return menuDtoMapper.toDtoList(list);
        }
        return new ArrayList<>();
    }


    @Override
    public List<String> getMenuKeyByMenuId(UUID menuId) {
        List<String> menuKey = new ArrayList<>();
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            menuKey.add(menu.get().getKey());
            if (Objects.nonNull(menu.get().getParent())) {
                menuKey.addAll(getMenuKeyByMenuId(menu.get().getParent().getId()));
            }
        }
        return menuKey;
    }

    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        menuRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }


}
