package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.MenuService;
import com.ream.core.service.security.dto.MenuDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/menus")
@Tag(name = "منو های تعریف شده در سیتم", description = "منو های تعریف شده در سیتم")
public class MenuController extends BaseController {






    @Autowired
    private MenuService menuService;


    @PostMapping
    public ActionResult<MenuDto> save(@RequestBody MenuDto menuDto, Locale locale) {
        isExist(menuDto, ModeType.CREATE, locale);
        try {
            return RESULT(menuService.save(menuDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" title key ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<MenuDto> update(@RequestBody MenuDto menuDto, Locale locale) {
        isExist(menuDto, ModeType.EDIT, locale);
        try {
            return RESULT(menuService.update(menuDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("title key", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<MenuDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<MenuDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<MenuDto> MenuDtoPageResponse;
        try {
            MenuDtoPageResponse = menuService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(MenuDtoPageResponse)) {
            return NO_CONTENT("MenuDtoPageResponse", locale);
        } else {
            return RESULT(MenuDtoPageResponse, locale);
        }

    }




    @GetMapping("/id/{id}")
    public ActionResult<Optional<MenuDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<MenuDto> optionalMenuDtoo;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalMenuDtoo = menuService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalMenuDtoo.isPresent()) {
            return RESULT(optionalMenuDtoo,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!menuService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(menuService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping("/list")
    public ActionResult<List<MenuDto>> list(Locale locale) {
        try {
            return RESULT(menuService.findAll(), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(MenuDto menuDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(menuDto.getId())) {
                Optional<MenuDto> optionalPermissionDto = menuService.findById(menuDto.getId());
                if (!optionalPermissionDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(menuDto.getKey())) {
            NO_CONTENT(" getKey ", locale);
        }
        if (Objects.isNull(menuDto.getTitle())) {
            NO_CONTENT(" getTitle ", locale);
        }
        if (Objects.nonNull(menuDto.getParentId())) {
            Optional<MenuDto> optionalMenuDto =
                    menuService.findById(menuDto.getParentId());
            if (!optionalMenuDto.isPresent()) {
                NOT_FOUND("getParentId", locale);
            }

        }

    }




}
