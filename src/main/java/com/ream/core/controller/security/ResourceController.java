package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.MenuService;
import com.ream.core.service.security.PermissionService;
import com.ream.core.service.security.ResourceService;
import com.ream.core.service.security.dto.MenuDto;
import com.ream.core.service.security.dto.PermissionDto;
import com.ream.core.service.security.dto.ResourceDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resources")
@Tag(name = "تعریف منابع سیستم", description = "تعریف منابع سیستم")
public class ResourceController extends BaseController {






    @Autowired
    private ResourceService resourceService;


    @PostMapping
    public ActionResult<ResourceDto> save(@RequestBody ResourceDto resourceDto, Locale locale) {
        isExist(resourceDto, ModeType.CREATE, locale);
        try {
            return RESULT(resourceService.save(resourceDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" key title url menu ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<ResourceDto> update(@RequestBody ResourceDto resourceDto, Locale locale) {
        isExist(resourceDto, ModeType.EDIT, locale);
        try {
            return RESULT(resourceService.update(resourceDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("key title url menu", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<ResourceDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<ResourceDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<ResourceDto> ResourceDtoPageResponse;
        try {
            ResourceDtoPageResponse = resourceService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(ResourceDtoPageResponse)) {
            return NO_CONTENT("ResourceDtoPageResponse", locale);
        } else {
            return RESULT(ResourceDtoPageResponse, locale);
        }

    }




    @GetMapping("/id/{id}")
    public ActionResult<Optional<ResourceDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<ResourceDto> optionalResourceDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalResourceDto = resourceService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalResourceDto.isPresent()) {
            return RESULT(optionalResourceDto,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!resourceService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(resourceService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }



    @Autowired
    private MenuService menuService;

    private void isExist(ResourceDto resourceDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(resourceDto.getId())) {
                Optional<ResourceDto> optionalResourceDto = resourceService.findById(resourceDto.getId());
                if (!optionalResourceDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(resourceDto.getKey())) {
            NO_CONTENT(" getKey ", locale);
        }
        if (Objects.isNull(resourceDto.getTitle())) {
            NO_CONTENT(" getTitle ", locale);
        }
        if (Objects.isNull(resourceDto.getUrl())) {
            NO_CONTENT(" getUrl ", locale);
        }



        if (Objects.nonNull(resourceDto.getMenuId())) {
            Optional<MenuDto> optionalMenuDto =
                    menuService.findById(resourceDto.getMenuId());
            if (!optionalMenuDto.isPresent()) {
                NOT_FOUND("getMenuId", locale);
            }

        } else {
            NO_CONTENT("getMenuId", locale);
        }





    }



}
