package com.ream.core.controller.baseInfo;

import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CommonBaseTypeService;
import com.ream.core.service.baseInfo.dto.TypeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/common-base-types")
@RequiredArgsConstructor
@Tag(name = "نوع اطلاعات پایه", description = "نوع اطلاعات پایه ")
public class CommonBaseTypeController extends BaseController {
    @Autowired
    CommonBaseTypeService commonBaseTypeService;

    @PostMapping
    public ActionResult<TypeDto> save(@RequestBody TypeDto commonBaseTypeDto, Locale locale) {
        isExist(commonBaseTypeDto, ModeType.CREATE, locale);
        try {
            return RESULT(commonBaseTypeService.save(commonBaseTypeDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" className & title ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<TypeDto> update(@RequestBody TypeDto commonBaseTypeDto, Locale locale) {
        isExist(commonBaseTypeDto, ModeType.EDIT, locale);
        try {
            return RESULT(commonBaseTypeService.update(commonBaseTypeDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" className & title ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<TypeDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<TypeDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<TypeDto> commonBaseTypeDtoPageResponse;
        try {
            commonBaseTypeDtoPageResponse = commonBaseTypeService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(commonBaseTypeDtoPageResponse)) {
            return NO_CONTENT("commonBaseType", locale);
        } else {
            return RESULT(commonBaseTypeDtoPageResponse, locale);
        }

    }


    @GetMapping("/title/{title}")
    @Operation(summary = "جستجو برا اساس عنوان نوع اطلاعات پایه", description = "return ActionResult<Optional<List<TypeDto>>>")
    public ActionResult<Optional<List<TypeDto>>> findByTitleContains(@PathVariable String title, Locale locale) {
        Optional<List<TypeDto>> commonBaseTypeDto;
        if (title.equals(null) || title.isEmpty() || title.isBlank()) {
            return NO_CONTENT("code =" + title, locale);
        }
        try {
            commonBaseTypeDto = commonBaseTypeService.findByTitleContains(title);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(commonBaseTypeDto, locale);
    }

    @GetMapping("/class-name-or-title")
    @Operation(summary = "جستجو برا اساس عنوان یا class-name نوع اطلاعات پایه", description = "return ActionResult<PageResponse<TypeDto>>")
    public ActionResult<PageResponse<TypeDto>> findAllByClassNameAndTitle(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam String title, @RequestParam String className, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<TypeDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<TypeDto> commonBaseTypeDtoPageResponse;
        try {
            commonBaseTypeDtoPageResponse = commonBaseTypeService.findByClassNameOrTitle(className, title, request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
//        if (Objects.isNull(commonBaseTypeDtoPageResponse)) {
//            return NO_CONTENT("commonBaseType", locale);
//        } else {
        return RESULT(commonBaseTypeDtoPageResponse, locale);
//        }

    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<TypeDto>> findById(@PathVariable Long id, Locale locale) {
        Optional<TypeDto> optionalCommonBaseTypeDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalCommonBaseTypeDto = commonBaseTypeService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalCommonBaseTypeDto.isPresent()) {
            return RESULT(optionalCommonBaseTypeDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @GetMapping("/class-name/{className}")
    @Operation(summary = "جستجو برا class-name نوع اطلاعات پایه", description = "return ActionResult<Optional<TypeDto>>")
    public ActionResult<Optional<TypeDto>> findByClassName(@PathVariable String className, Locale locale) {
        Optional<TypeDto> optionalCommonBaseTypeDto;
        if (className.equals(null)) {
            return NO_CONTENT("id =" + className, locale);
        }
        try {
            optionalCommonBaseTypeDto = commonBaseTypeService.findByClassName(className);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalCommonBaseTypeDto.isPresent()) {
            return RESULT(optionalCommonBaseTypeDto, locale);
        } else {
            return RESULT(Optional.empty(), locale);
        }

    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable Long id, Locale locale) {
        if (Objects.isNull(id)) {
            return NO_CONTENT("id", locale);
        }
        if (!commonBaseTypeService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(commonBaseTypeService.deleteById(id),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("id is used", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(TypeDto commonBaseTypeDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(commonBaseTypeDto.getId())) {
                Optional<TypeDto> optionalCommonBaseTypeDto = commonBaseTypeService.findById(commonBaseTypeDto.getId());
                if (!optionalCommonBaseTypeDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(commonBaseTypeDto.getClassName())) {
            NO_CONTENT(" className ", locale);
        }
        if (Objects.isNull(commonBaseTypeDto.getTitle())) {
            NO_CONTENT(" title ", locale);
        }
    }
}