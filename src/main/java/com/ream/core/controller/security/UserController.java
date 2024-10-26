package com.ream.core.controller.security;

import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.UserDto;
import com.ream.core.service.security.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(name = "کاربران سیستم", description = "کاربران سیستم")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;


    @PostMapping
    public ActionResult<UserDto> save(@RequestBody UserDto userDto, Locale locale) {
        isExist(userDto, ModeType.CREATE, locale);
        try {
            return RESULT(userService.save(userDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" userName ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<UserDto> update(@RequestBody UserDto userDto, Locale locale) {
        isExist(userDto, ModeType.EDIT, locale);
        try {
            return RESULT(userService.update(userDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("userName", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<UserDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
//        userService.updateALlPassword();
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<UserDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<UserDto> UserDtoPageResponse;
        try {
            UserDtoPageResponse = userService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(UserDtoPageResponse)) {
            return NO_CONTENT("UserDtoPageResponse", locale);
        } else {
            return RESULT(UserDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<UserDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<UserDto> optionalUserDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalUserDto = userService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalUserDto.isPresent()) {
            return RESULT(optionalUserDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }


    @GetMapping("/user-name/{username}")
    public ActionResult<Optional<UserDto>> findByUserName(@PathVariable String username, Locale locale) {
        UserDto optionalUserDto;
        if (username.equals(null)) {
            return NO_CONTENT(" username= ", locale);
        }
        if (username.trim().isEmpty()) {
            return NO_CONTENT(" username ", locale);
        }
        try {
            optionalUserDto = userService.getUserByUserName(username.trim());
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.nonNull(optionalUserDto)) {
            return RESULT(Optional.ofNullable(optionalUserDto), locale);
        } else {
            return RESULT(Optional.empty(), locale);

        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!userService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(userService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(UserDto userDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(userDto.getId())) {
                Optional<UserDto> optionalUserDto = userService.findById(userDto.getId());
                if (!optionalUserDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(userDto.getUsername())) {
            NO_CONTENT(" username ", locale);
        }
        if (modeType.equals(ModeType.CREATE)){
            if (Objects.isNull(userDto.getPassword())) {
                NO_CONTENT(" password ", locale);
            }

        }



        if (Objects.isNull(userDto.getEnabledUser())) {
            NO_CONTENT(" getEnabledUser ", locale);
        }
        if (Objects.isNull(userDto.getPasswordMustChange())) {
            NO_CONTENT(" getPasswordMustChange ", locale);
        }


    }


}
