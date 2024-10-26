package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.AccessService;
import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.ResourcePermissionService;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.dto.*;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/api/access")
@Tag(name = "دسترسی نقش به منابع -سطح دسترسی", description = "دسترسی نقش به منابع تعریف شده ")
public class AccessController extends BaseController {


    @Autowired
    private AccessService accessService;


    @PostMapping
    @Operation(summary = "ذخیره(در همه controller ها وجود دارد)", description = "Returns memberDto")

    public ActionResult<AccessDto> save(@RequestBody AccessDto accessDto, Locale locale) {
        isExist(accessDto, ModeType.CREATE, locale);
        try {
            return RESULT(accessService.save(accessDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" role_Id and resource_permission_id ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    @Operation(summary = "ویرایش(در همه controller ها وجود دارد)", description = "Returns memberDto")

    public ActionResult<AccessDto> update(@RequestBody AccessDto accessDto, Locale locale) {
        isExist(accessDto, ModeType.EDIT, locale);
        try {
            return RESULT(accessService.update(accessDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("role_Id and resource_permission_id", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    @Operation(summary = "نمایش همه(در همه controller ها وجود دارد)", description = "Returns PageResponse<MemberDto>")

    public ActionResult<PageResponse<AccessDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<AccessDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<AccessDto> AccessDtoPageResponse;
        try {
            AccessDtoPageResponse = accessService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(AccessDtoPageResponse)) {
            return NO_CONTENT("AccessDtoPageResponse", locale);
        } else {
            return RESULT(AccessDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    @Operation(summary = "جستجو بر اساس id(در همه controller ها وجود دارد)", description = "Returns Optional<MemberDto>")

    public ActionResult<Optional<AccessDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<AccessDto> optionalAccessDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalAccessDto = accessService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalAccessDto.isPresent()) {
            return RESULT(optionalAccessDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }


    @GetMapping("/access-user")
    @Operation(summary = "لیست تمام دسترس های user", description = "ActionResult<Optional<AccessRoleDto>> ")

    public ActionResult<Optional<AccessRoleDto>> getAccessRoleDtoByCurrentUser(Locale locale) {
        Optional<AccessRoleDto> accessRoleDto = Optional.empty();
        try {
            accessRoleDto = accessService.getAccessRoleDtoByCurrentUser();
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
            return RESULT(accessRoleDto, locale);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "حذف(در همه controller ها وجود دارد)", description = "Returns Boolean")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!accessService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(accessService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @Autowired
    private RoleService roleService;

    @Autowired
    private ResourcePermissionService resourcePermissionService;

    private void isExist(AccessDto accessDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(accessDto.getId())) {
                Optional<AccessDto> optionalAccessDto = accessService.findById(accessDto.getId());
                if (!optionalAccessDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.nonNull(accessDto.getRoleId())) {
            Optional<RoleDto> optionalRoleDto =
                    roleService.findById(accessDto.getRoleId());
            if (!optionalRoleDto.isPresent()) {
                NOT_FOUND("getRoleId", locale);
            }

        } else {
            NO_CONTENT("getRoleId", locale);
        }

        if (Objects.nonNull(accessDto.getResourcePermissionId())) {
            Optional<ResourcePermissionDto> optionalResourcePermissionDto =
                    resourcePermissionService.findById(accessDto.getResourcePermissionId());
            if (!optionalResourcePermissionDto.isPresent()) {
                NOT_FOUND("getResourcePermissionId", locale);
            }

        } else {
            NO_CONTENT("getResourcePermissionId", locale);
        }


    }


}
