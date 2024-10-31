package com.ream.core.controller.baseInfo;

import com.ream.core.controller.BaseController;
import com.ream.core.domain.baseInfo.CommonBaseData;
import com.ream.core.model.ModeType;
import com.ream.core.repository.baseInfo.CommonBaseDataRepository;
import com.ream.core.repository.baseInfo.CommonBaseTypeRepository;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CommonBaseDataService;
import com.ream.core.service.baseInfo.CommonBaseTypeService;
import com.ream.core.service.baseInfo.dto.CommonBaseDataDto;
import com.ream.core.service.baseInfo.dto.CommonBaseTypeDto;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/common-base-data")
@RequiredArgsConstructor
@Tag(name = "اطلاعات پایه", description = "اطلاعات پایه ")
public class CommonBaseDataController extends BaseController {
    @Autowired
   CommonBaseDataService commonBaseDataService;
    @Autowired CommonBaseTypeService commonBaseTypeService;
    @Autowired CommonBaseDataRepository commonBaseDataRepository;
    @Autowired CommonBaseTypeRepository commonBaseTypeRepository;

    @PostMapping
    public ActionResult<CommonBaseDataDto> save(@RequestBody CommonBaseDataDto commonBaseDataDto, Locale locale) {
        isExist(commonBaseDataDto, ModeType.CREATE, locale);
        Optional<List<CommonBaseData>> resultOptional = commonBaseDataRepository.findByCommonBaseType(commonBaseTypeRepository.findById(commonBaseDataDto.getCommonBaseTypeId()).get());
        if (resultOptional.isPresent()) {
            if (resultOptional.get().stream().filter(f -> f.getKey().equals(commonBaseDataDto.getKey())).collect(Collectors.toList()).size() > 0)
                return CONFLICT("key", locale);
        }
        try {
            return RESULT(commonBaseDataService.save(commonBaseDataDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("value", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<CommonBaseDataDto> update(@RequestBody CommonBaseDataDto commonBaseDataDto, Locale locale) {
        isExist(commonBaseDataDto, ModeType.EDIT, locale);
        try {
            return RESULT(commonBaseDataService.update(commonBaseDataDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("value", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<CommonBaseDataDto>> findAll(@RequestParam int currentPage,
                                                                 @RequestParam int pageSize,
                                                                 Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<CommonBaseDataDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<CommonBaseDataDto> commonBaseDataDtoPageResponse;
        try {
            commonBaseDataDtoPageResponse = commonBaseDataService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(commonBaseDataDtoPageResponse)) {
            return NO_CONTENT("commonBaseDataDtoPageResponse", locale);
        } else {
            return RESULT(commonBaseDataDtoPageResponse,locale);
        }

    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<CommonBaseDataDto>> findById(@PathVariable Long id, Locale locale) {
        Optional<CommonBaseDataDto> optionalCommonBaseDataDto;
        if (id.equals(null)) {
            return NO_CONTENT("id =" + id, locale);
        }
        try {
            optionalCommonBaseDataDto = commonBaseDataService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalCommonBaseDataDto.isPresent()) {
            return RESULT(optionalCommonBaseDataDto,locale);
        } else {
            return NOT_FOUND("commonBaseData", locale);
        }
    }

    @GetMapping("find-all/class-name/{className}")
    @Operation(summary = "جستجو برا اساس className نوع اطلاعات پایه", description = "return ActionResult<PageResponse<DataDto>>")
    public ActionResult<PageResponse<CommonBaseDataDto>> findByClassName(@PathVariable String className,
                                                                         @RequestParam int currentPage,
                                                                         @RequestParam int pageSize,
                                                                         Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage <= 0 || pageSize <= 0 } ", locale);
        }
        PageRequest<CommonBaseDataDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<CommonBaseDataDto> commonBaseDataDtoPageResponse = new PageResponse<>();
        if (className.equals(null) && className.isEmpty()) {
            return NO_CONTENT(" className= " + className, locale);
        }
        try {
            commonBaseDataDtoPageResponse = commonBaseDataService.findByClassName(className, request);
        } catch (Exception exception) {
            INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
//        if (Objects.isNull(commonBaseDataDtoPageResponse)) {
//            return RESULT(commonBaseDataDtoPageResponse);
//        } else if (commonBaseDataDtoPageResponse.getRows().size() == 0) {
//            return NO_CONTENT(className, locale);
//        } else {
        return RESULT(commonBaseDataDtoPageResponse,locale);

//        }
    }


    @GetMapping("/class-name/{className}")
    @Operation(summary = "جستجو برا اساس className نوع اطلاعات پایه", description = "return ActionResult<Optional<List<DataDto>>>")
    public ActionResult<Optional<List<CommonBaseDataDto>>> findAllByClassName(@PathVariable String className, Locale locale) {
        if (Objects.isNull(className)) return NO_CONTENT("classNameNotFund", locale);
        if (className.trim().isEmpty()) return NO_CONTENT("classNameNotFund", locale);
        if (className.trim().isBlank()) return NO_CONTENT("classNameNotFund", locale);
        try {
            return RESULT(commonBaseDataService.findAllByClassName(className),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping("/class-name-value/{className}/{value}")
    @Operation(summary = "جستجو برا اساس className نوع اطلاعات پایه و همچنین value خود اطلاعات پایه", description = "return ActionResult<PageResponse<DataDto>>")
    public ActionResult<PageResponse<CommonBaseDataDto>> findByValueAndType(@PathVariable String value,
                                                                            @PathVariable String className,
                                                                            @RequestParam int currentPage,
                                                                            @RequestParam int pageSize,
                                                                            Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage <= 0 || pageSize <= 0 } ", locale);
        }
        PageRequest<CommonBaseDataDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<CommonBaseDataDto> commonBaseDataDtoPageResponse = new PageResponse<>();

        if (className.equals(null) || className.isEmpty() || className.isBlank()) {
            return NO_CONTENT(" className= " + className, locale);
        }
        if (value.equals(null) || value.isEmpty() || value.isBlank()) {
            return findByClassName(className, currentPage, pageSize, locale);
        }
        try {
            commonBaseDataDtoPageResponse = commonBaseDataService.findByValueAndType(value, className, request);
        } catch (Exception exception) {
            INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
//        if (Objects.isNull(commonBaseDataDtoPageResponse)) {
//            return NOT_FOUND(value, locale);
//        } else if (commonBaseDataDtoPageResponse.getRows().size() == 0) {
//            return NO_CONTENT(value, locale);
//        } else {
        return RESULT(commonBaseDataDtoPageResponse,locale);

//        }
    }


    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable Long id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!commonBaseDataService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(commonBaseDataService.deleteById(id),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("id is used", locale);

        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(CommonBaseDataDto commonBaseDataDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(commonBaseDataDto.getId())) {
                Optional<CommonBaseDataDto> optionalCommonBaseDataDto =
                        commonBaseDataService.findById(commonBaseDataDto.getId());
                if (!optionalCommonBaseDataDto.isPresent()) {
                    NOT_FOUND("id", locale);
                }
            } else {
                NO_CONTENT("id", locale);
            }
        }
        Long commonBaseTypeId = commonBaseDataDto.getCommonBaseTypeId();
        if (Objects.nonNull(commonBaseTypeId)) {
            if (Objects.nonNull(commonBaseDataDto.getValue())) {
                if (Objects.nonNull(commonBaseDataDto.getKey())) {
                    Optional<CommonBaseTypeDto> optionalCommonBaseType =
                            commonBaseTypeService.findById(commonBaseTypeId);
                    if (!optionalCommonBaseType.isPresent()) {
                        NOT_FOUND("commonBaseType", locale);
                    }
                } else {
                    NO_CONTENT("key", locale);
                }
            } else {
                NO_CONTENT("value", locale);
            }
        } else {
            NO_CONTENT("commonBaseTypeId", locale);
        }
    }
}