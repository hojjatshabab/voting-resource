package com.ream.core.controller.security;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.RoleService;
import com.ream.core.service.security.UserRoleService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.security.dto.RoleDto;
import com.ream.core.service.security.dto.UserDto;
import com.ream.core.service.security.dto.UserRoleDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-roles")
@Tag(name = "ارتباط  کاربران و نقش های سیستم", description = "ارتباط  کاربران و نقش های سیستم")
public class UserRoleController extends BaseController {


    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;


    @Autowired
    private UserService userService;


    @PostMapping
    public ActionResult<UserRoleDto> save(@RequestBody UserRoleDto userRoleDto, Locale locale) {
        isExist(userRoleDto, ModeType.CREATE, locale);
        try {
            return RESULT(userRoleService.save(userRoleDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" userId,roleId ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<UserRoleDto> update(@RequestBody UserRoleDto userRoleDto, Locale locale) {
        isExist(userRoleDto, ModeType.EDIT, locale);
        try {
            return RESULT(userRoleService.update(userRoleDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("userId,roleId", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<UserRoleDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<UserRoleDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<UserRoleDto> UserRoleDtoPageResponse;
        try {
            UserRoleDtoPageResponse = userRoleService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(UserRoleDtoPageResponse)) {
            return NO_CONTENT("UserRoleDtoPageResponse", locale);
        } else {
            return RESULT(UserRoleDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<UserRoleDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<UserRoleDto> optionalUserRoleDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalUserRoleDto = userRoleService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalUserRoleDto.isPresent()) {
            return RESULT(optionalUserRoleDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }


    @GetMapping("/username/{username}")
    public ActionResult<Optional<List<UserRoleDto>>> findByUserName(@PathVariable String username, Locale locale) {
        Optional<List<UserRoleDto>> optionalUserRoleDto;
        if (Objects.isNull(username)) {
            return NO_CONTENT(" username ", locale);
        }

        try {
            UserDto userDto = userService.getUserByUserName(username);
            if (Objects.isNull(userDto)) return NOT_FOUND("username", locale);
            optionalUserRoleDto = userRoleService.findByUserId(userDto.getId());
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(optionalUserRoleDto, locale);
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!userRoleService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(userRoleService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(UserRoleDto userRoleDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(userRoleDto.getId())) {
                Optional<UserRoleDto> optionalUserRoleDto = userRoleService.findById(userRoleDto.getId());
                if (!optionalUserRoleDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.nonNull(userRoleDto.getRoleId())) {
            Optional<RoleDto> optionalRoleDto =
                    roleService.findById(userRoleDto.getRoleId());
            if (!optionalRoleDto.isPresent()) {
                NOT_FOUND("getRoleId", locale);
            }

        } else {
            NO_CONTENT("getRoleId", locale);
        }


        if (Objects.nonNull(userRoleDto.getUserId())) {
            Optional<UserDto> optionalUserDto =
                    userService.findById(userRoleDto.getUserId());
            if (!optionalUserDto.isPresent()) {
                NOT_FOUND("optionalUserDto", locale);
            }

        } else {
            NO_CONTENT("optionalUserDto", locale);
        }


    }
}
