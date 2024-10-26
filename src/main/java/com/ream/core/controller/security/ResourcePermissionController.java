package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.*;
import com.ream.core.service.security.dto.*;
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
@RequestMapping("/api/resource-permissions")
@Tag(name = "تعریف ارتباط منابع به سطح دسترسی سیستم", description = "تعریف ارتباط منابع به سطح دسترسی سیستم")
public class ResourcePermissionController extends BaseController {

    @Autowired
    private ResourcePermissionService resourcePermissionService;


    @PostMapping
    public ActionResult<ResourcePermissionDto> save(@RequestBody ResourcePermissionDto resourcePermissionDto, Locale locale) {
        isExist(resourcePermissionDto, ModeType.CREATE, locale);
        try {
            return RESULT(resourcePermissionService.save(resourcePermissionDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" permission_id and resource__id ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<ResourcePermissionDto> update(@RequestBody ResourcePermissionDto resourcePermissionDto, Locale locale) {
        isExist(resourcePermissionDto, ModeType.EDIT, locale);
        try {
            return RESULT(resourcePermissionService.update(resourcePermissionDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("permission_id and resource__id", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<ResourcePermissionDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<ResourcePermissionDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<ResourcePermissionDto> ResourcePermissionDtoPageResponse;
        try {
            ResourcePermissionDtoPageResponse = resourcePermissionService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(ResourcePermissionDtoPageResponse)) {
            return NO_CONTENT("ResourcePermissionDtoPageResponse", locale);
        } else {
            return RESULT(ResourcePermissionDtoPageResponse, locale);
        }

    }




    @GetMapping("/id/{id}")
    public ActionResult<Optional<ResourcePermissionDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<ResourcePermissionDto> optionalResourcePermissionDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalResourcePermissionDto = resourcePermissionService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalResourcePermissionDto.isPresent()) {
            return RESULT(optionalResourcePermissionDto,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!resourcePermissionService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(resourcePermissionService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private PermissionService permissionService;

    private void isExist(ResourcePermissionDto resourcePermissionDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(resourcePermissionDto.getId())) {
                Optional<ResourcePermissionDto> optionalResourcePermissionDto = resourcePermissionService.findById(resourcePermissionDto.getId());
                if (!optionalResourcePermissionDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.nonNull(resourcePermissionDto.getResourceId())) {
            Optional<ResourceDto> optionalResourceDto =
                    resourceService.findById(resourcePermissionDto.getResourceId());
            if (!optionalResourceDto.isPresent()) {
                NOT_FOUND("getResourceId", locale);
            }

        } else {
            NO_CONTENT("getResourceId", locale);
        }

        if (Objects.nonNull(resourcePermissionDto.getPermissionId())) {
            Optional<PermissionDto> optionalPermissionDto =
                    permissionService.findById(resourcePermissionDto.getPermissionId());
            if (!optionalPermissionDto.isPresent()) {
                NOT_FOUND("getPermissionId", locale);
            }

        } else {
            NO_CONTENT("getPermissionId", locale);
        }




    }


}
