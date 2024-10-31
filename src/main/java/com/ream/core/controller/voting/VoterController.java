package com.ream.core.controller.voting;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.VoterService;
import com.ream.core.service.voting.dto.MemberDto;
import com.ream.core.service.voting.dto.VoterDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/voters")
@Tag(name = "رای دهندگان", description = "کسانی که مجاز به رای دادن هستند.")
public class VoterController extends BaseController {
    private final VoterService voterService;

    public VoterController(VoterService voterService) {
        this.voterService = voterService;
    }

    @PostMapping
    public ActionResult<VoterDto> save(@RequestBody VoterDto voterDto, Locale locale) {
        isExist(voterDto, ModeType.CREATE, locale);
        try {
            return RESULT(voterService.save(voterDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<VoterDto> update(@RequestBody VoterDto voterDto, Locale locale) {
        isExist(voterDto, ModeType.EDIT, locale);
        try {
            return RESULT(voterService.update(voterDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<VoterDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<VoterDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<VoterDto> voterDtoPageResponse;
        try {
            voterDtoPageResponse = voterService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(voterDtoPageResponse)) {
            return NO_CONTENT(" voterDtoPageResponse ", locale);
        } else {
            return RESULT(voterDtoPageResponse, locale);
        }
    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<VoterDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<VoterDto> optionalVoterDto;
        try {
            optionalVoterDto = voterService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalVoterDto.isPresent()) {
            return RESULT(optionalVoterDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!voterService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(voterService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(VoterDto voterDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(voterDto.getId())) {
                Optional<VoterDto> optionalVoterDto = voterService.findById(voterDto.getId());
                if (!optionalVoterDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
    }
}
