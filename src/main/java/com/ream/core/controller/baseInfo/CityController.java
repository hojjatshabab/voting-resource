package com.ream.core.controller.baseInfo;


import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.CityService;
import com.ream.core.service.baseInfo.dto.CityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("api/cities")
@RequiredArgsConstructor
@Tag(name = "شهر ها", description = "شهر ها ")

public class CityController extends BaseController {

    @Autowired
    CityService cityService;

    @PostMapping
    public ActionResult<CityDto> save(@RequestBody CityDto cityDto, Locale locale) {
        isExist(cityDto, ModeType.CREATE, locale);
        try {
            return RESULT(cityService.save(cityDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" code ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<CityDto> update(@RequestBody CityDto cityDto, Locale locale) {
        isExist(cityDto, ModeType.EDIT, locale);
        try {
            return RESULT(cityService.update(cityDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("code", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @GetMapping
    public ActionResult<PageResponse<CityDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, @RequestParam String province, @RequestParam String name, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<CityDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<CityDto> cityDtoPageResponse;
        try {
            Long valueProvince;
            if (province.isEmpty() || province.isBlank() || province == null) valueProvince = null;
            else valueProvince = Long.valueOf(province);
            cityDtoPageResponse = cityService.findAll(valueProvince, name, request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(cityDtoPageResponse)) {
            return NO_CONTENT("city", locale);
        } else {
            return RESULT(cityDtoPageResponse,locale);
        }

    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<CityDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<CityDto> optionalCityDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalCityDto = cityService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalCityDto.isPresent()) {
            return RESULT(optionalCityDto,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!cityService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(cityService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping("/code/{code}")
    @Operation(summary = "جستجو برا اسا کذ شهر", description = "Returns ActionResult<Optional<CityDto>>")
    public ActionResult<Optional<CityDto>> findByCode(@PathVariable String code, Locale locale) {
        CityDto cityDto;
        if (code.equals(null)) {
            return NO_CONTENT("code =" + code, locale);
        }
        try {
            cityDto = cityService.findByCode(code);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(Optional.ofNullable(cityDto),locale);

    }


    @GetMapping("/name/{name}")
    @Operation(summary = "جستجو برا اسا نام شهر", description = "Returns ActionResult<Optional<CityDto>>")
    public ActionResult<Optional<List<CityDto>>> findByNameContains(@PathVariable String name, Locale locale) {
        Optional<List<CityDto>> cityDtos;
        if (name.equals(null) || name.isEmpty() || name.isBlank()) {
            return NO_CONTENT("code =" + name, locale);
        }
        try {
            cityDtos = cityService.findByNameContains(name);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(cityDtos,locale);
    }


    @GetMapping("/province/{province}")
    @Operation(summary = "جستجو برا اسا id استان ", description = "Returns ActionResult<Optional<List<CityDto>>>")
    public ActionResult<Optional<List<CityDto>>> findByProvince(@PathVariable Long province, Locale locale) {
        List<CityDto> cityDtoList = new ArrayList<>();
        if (province.equals(null)) {
            return NO_CONTENT("code =" + province, locale);
        }
        try {
            cityDtoList = cityService.findByCommonBaseDataProvince(province);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(Optional.ofNullable(cityDtoList),locale);

    }

    private void isExist(CityDto cityDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(cityDto.getId())) {
                Optional<CityDto> optionalCityDto = cityService.findById(cityDto.getId());
                if (!optionalCityDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (modeType.equals(ModeType.CREATE)) {
            if (Objects.nonNull(cityDto.getCode())) {
                if (Objects.nonNull(cityService.findByCode(cityDto.getCode()))) {
                    CONFLICT(" code ", locale);
                }
            } else {
                NO_CONTENT(" code ", locale);
            }

        }

    }

}
