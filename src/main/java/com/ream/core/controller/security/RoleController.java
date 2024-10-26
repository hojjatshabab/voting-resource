package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.UserDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
@Tag(name = "نقش های سیستم", description = "نقش های سیستم")
public class RoleController extends BaseController {


    @Autowired
    private RoleService roleService;


    @PostMapping
    public ActionResult<RoleDto> save(@RequestBody RoleDto roleDto, Locale locale) {
        isExist(roleDto, ModeType.CREATE, locale);
        try {
            return RESULT(roleService.save(roleDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" role_latin_name or role_latin_name ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<RoleDto> update(@RequestBody RoleDto roleDto, Locale locale) {
        isExist(roleDto, ModeType.EDIT, locale);
        try {
            return RESULT(roleService.update(roleDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("role_latin_name or role_latin_name", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @Autowired
    UserService userService;

    @GetMapping("/role-not-user/{username}")
    public ActionResult<Optional<List<RoleDto>>> findRoleNotUser(@PathVariable String username, Locale locale) {
        Optional<List<RoleDto>> roleDtoList = Optional.empty();
        if (Objects.isNull(username)) return NO_CONTENT("username", locale);
        try {
            UserDto userDto = userService.getUserByUserName(username);
            if (Objects.isNull(userDto)) {
                NOT_FOUND("username", locale);
            }
            roleDtoList = roleService.findRoleNotUser(userDto.getId());
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(roleDtoList, locale);
    }


    @GetMapping("/list")
    public ActionResult<List<RoleDto>> list(Locale locale) {
        try {
            return RESULT(roleService.findAll(), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<RoleDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<RoleDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<RoleDto> RoleDtoPageResponse;
        try {
            RoleDtoPageResponse = roleService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(RoleDtoPageResponse)) {
            return NO_CONTENT("RoleDtoPageResponse", locale);
        } else {
            return RESULT(RoleDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<RoleDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<RoleDto> optionalRoleDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalRoleDto = roleService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalRoleDto.isPresent()) {
            return RESULT(optionalRoleDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!roleService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(roleService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(RoleDto roleDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(roleDto.getId())) {
                Optional<RoleDto> optionalRoleDto = roleService.findById(roleDto.getId());
                if (!optionalRoleDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(roleDto.getRoleLatinName())) {
            NO_CONTENT(" getRoleLatinName ", locale);
        }
        if (Objects.isNull(roleDto.getRolePersianName())) {
            NO_CONTENT(" getRolePersianName ", locale);
        }


    }

}
