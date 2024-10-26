package com.ream.core.controller.baseInfo;

import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.OrganizationUnitService;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("api/organizations")
@RequiredArgsConstructor
@Tag(name = "ساختارسازمانی", description = "ساختارسازمانی ")
public class OrganizationUnitController extends BaseController {

    @Autowired
    OrganizationUnitService organizationUnitService;

    @PostMapping
    @Operation(summary = "ذخیره", description = "ActionResult<OrganizationUnitDto>")

    public ActionResult<OrganizationUnitDto> save(@RequestBody OrganizationUnitDto organizationUnitDto, Locale locale) {
        isExist(organizationUnitDto, ModeType.CREATE, locale);
        try {
            return RESULT(organizationUnitService.save(organizationUnitDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("value", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    @Operation(summary = "ویرایش", description = "ActionResult<OrganizationUnitDto>")

    public ActionResult<OrganizationUnitDto> update(@RequestBody OrganizationUnitDto organizationUnitDto, Locale locale) {
        isExist(organizationUnitDto, ModeType.EDIT, locale);
        try {
            return RESULT(organizationUnitService.update(organizationUnitDto),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("value", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping("/root-organization")
    @Operation(summary = "بازگشت تمام ساختار سازمانی به صورت درختی از مبدا", description = "ActionResult<OrganizationUnitDto>")
    public ActionResult<OrganizationUnitDto> getRootOrganization(Locale locale) {
        try {
            return RESULT(organizationUnitService.findByParentIdIsNull(),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    @Operation(summary = "مشاهده تمام  به صورت جدولی ", description = "ActionResult<PageResponse<OrganizationUnitDto>>")
    public ActionResult<PageResponse<OrganizationUnitDto>> findAll(@RequestParam int currentPage,
                                                                   @RequestParam int pageSize,
                                                                   Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<OrganizationUnitDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<OrganizationUnitDto> organizationUnitDtoResponse;
        try {
            organizationUnitDtoResponse = organizationUnitService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(organizationUnitDtoResponse)) {
            return NO_CONTENT("commonBaseDataDtoPageResponse", locale);
        } else {
            return RESULT(organizationUnitDtoResponse,locale);
        }

    }

    @GetMapping("/id/{id}")
    @Operation(summary = "جستجو بر اساس id ساختار سازمانی ", description = " ActionResult<Optional<OrganizationUnitDto>>")
    public ActionResult<Optional<OrganizationUnitDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<OrganizationUnitDto> optionalOrganizationUnitDto;
        if (id == null) {
            return NO_CONTENT("id =" + id, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return NOT_FOUND("commonBaseData", locale);
        }
    }

    @GetMapping("/find-all-children-by-code-path/{codePath}")
    @Operation(summary = "مشاهده تمام زیر مجموعه یک ساختار سازمانی همراه با پدر خود بر اساس codePath پدر", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")
    public ActionResult<Optional<List<OrganizationUnitDto>>> findAllChildrenByCodePath(@PathVariable String codePath, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (codePath == null || codePath.isEmpty()) {
            return NO_CONTENT("codePath =" + codePath, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findAllChildrenByCodePath(codePath);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @PostMapping("/change-node-by-parent-id/{parentId}")
    @Operation(summary = "جابه جای node خود درخت سازمانی ", description = "به این صورت که در body خود OrganizationUnitDto و در url درخواست parentId جدید را ارسال میکنیدو return ActionResult<Optional<List<OrganizationUnitDto>>>")
    public ActionResult<Optional<List<OrganizationUnitDto>>> changeNodeByParentId(@PathVariable String parentId, @RequestBody OrganizationUnitDto organizationUnitDto, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (parentId == null || parentId.isEmpty()) {
            return NO_CONTENT("codePath =" + parentId, locale);
        }
        if (organizationUnitDto.getId() == null) {
            return NO_CONTENT("organizationUnitDto is null ", locale);
        }
        if (organizationUnitDto.getParentId() == null) {
            return NO_CONTENT("organizationUnitDto parentId is null ", locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.changeNodeByParentId(parentId, organizationUnitDto);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @GetMapping("/find-all-children-by-parent-id/{id}")
    @Operation(summary = "مشاهده تمام زیر مجموعه درخت سازمانی به همراه پدر بر اساس id پدر ", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")

    public ActionResult<Optional<List<OrganizationUnitDto>>> findAllChildrenByCodePath(@PathVariable UUID id, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (id == null) {
            return NO_CONTENT("id =" + id, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findAllChildrenById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @GetMapping("/find-children-by-parent-id/{id}")
    @Operation(summary = "مشاهده تمام یک سطح زیر مجموعه درخت سازمانی بدون پدر خود  بر اساس id پدر ", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")
    public ActionResult<Optional<List<OrganizationUnitDto>>> findChildrenByCodePath(@PathVariable UUID id, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (id == null) {
            return NO_CONTENT("id =" + id, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findChildrenById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @GetMapping("/find-children-by-parent-code-path/{codePath}")
    @Operation(summary = "مشاهده تمام یک سطح زیر مجموعه درخت سازمانی بدون پدر خود  بر اساس codePath پدر ", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")

    public ActionResult<Optional<List<OrganizationUnitDto>>> findChildrenByCodePath(@PathVariable String codePath, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (codePath == null) {
            return NO_CONTENT("codePath =" + codePath, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findChildrenByCodePath(codePath);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @GetMapping("/find-organization-by-code/{code}")
    @Operation(summary = "جستجو بر اساس code ", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")
    public ActionResult<Optional<List<OrganizationUnitDto>>> findOrganizationByCode(@PathVariable String code, Locale locale) {
        Optional<List<OrganizationUnitDto>> optionalOrganizationUnitDto;
        if (code == null || code.isEmpty()) {
            return NO_CONTENT("code =" + code, locale);
        }
        try {
            optionalOrganizationUnitDto = organizationUnitService.findByCode(code);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalOrganizationUnitDto.isPresent()) {
            return RESULT(optionalOrganizationUnitDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }

    @GetMapping("/name/{name}")
    @Operation(summary = "جستجو بر اساس name ", description = "ActionResult<Optional<List<OrganizationUnitDto>>>")

    public ActionResult<Optional<List<OrganizationUnitDto>>> findByNameContains(@PathVariable String name, Locale locale) {
        Optional<List<OrganizationUnitDto>> organizationUnitsDto;
        if (name.equals(null) || name.isEmpty() || name.isBlank()) {
            return NO_CONTENT("name =" + name, locale);
        }
        try {
            organizationUnitsDto = organizationUnitService.findByNameContains(name);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(organizationUnitsDto,locale);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "حذف براساس  id ", description = "Boolean و اگر زیر مجموعه داشته باشد حذف نمیگردد")

    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!organizationUnitService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        if (organizationUnitService.findAllChildrenById(id).get().size() > 1) {
            return CONFLICT("id", locale);
        }
        try {
            return RESULT(organizationUnitService.deleteById(id),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("id is used", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(OrganizationUnitDto organizationUnitDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(organizationUnitDto.getId())) {
                Optional<OrganizationUnitDto> optionalOrganizationUnitDto =
                        organizationUnitService.findById(organizationUnitDto.getId());
                if (!optionalOrganizationUnitDto.isPresent()) {
                    NOT_FOUND("id", locale);
                }
            } else {
                NO_CONTENT("id", locale);
            }
        } else {
            String organizationName = organizationUnitDto.getName();
            if (Objects.nonNull(organizationName)) {

                Optional<OrganizationUnitDto> optionalOrganizationUnitDto =
                        organizationUnitService.findByName(organizationName);
                if (optionalOrganizationUnitDto.isPresent()) {
                    CONFLICT("organizationName", locale);
                }
            }

            if (Objects.nonNull(organizationUnitDto.getParentId())) {
                if (!organizationUnitService.findById(organizationUnitDto.getParentId()).isPresent())
                    NOT_FOUND("parentId", locale);
            } else {
                NO_CONTENT("parentId", locale);
            }

        }
    }
}