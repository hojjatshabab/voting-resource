package com.ream.core.controller.voting;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.CandidateService;
import com.ream.core.service.voting.VoterCandidateService;
import com.ream.core.service.voting.dto.CandidateDto;
import com.ream.core.service.voting.dto.VoterCandidateDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/voter-candidates")
@Tag(name = "آرای نامزدها", description = "کسانی که به نمایندها رای داده اند مشخص میشوند.")
public class VoterCandidateController extends BaseController {
    private final VoterCandidateService voterCandidateService;

    public VoterCandidateController(VoterCandidateService voterCandidateService) {
        this.voterCandidateService = voterCandidateService;
    }

    @PostMapping
    public ActionResult<VoterCandidateDto> save(@RequestBody VoterCandidateDto voterCandidateDto, Locale locale) {
        isExist(voterCandidateDto, ModeType.CREATE, locale);
        try {
            return RESULT(voterCandidateService.save(voterCandidateDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<VoterCandidateDto> update(@RequestBody VoterCandidateDto voterCandidateDto, Locale locale) {
        isExist(voterCandidateDto, ModeType.EDIT, locale);
        try {
            return RESULT(voterCandidateService.update(voterCandidateDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<VoterCandidateDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<VoterCandidateDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<VoterCandidateDto> voterCandidateDtoPageResponse;
        try {
            voterCandidateDtoPageResponse = voterCandidateService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(voterCandidateDtoPageResponse)) {
            return NO_CONTENT(" voterCandidateDtoPageResponse ", locale);
        } else {
            return RESULT(voterCandidateDtoPageResponse, locale);
        }
    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<VoterCandidateDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<VoterCandidateDto> optionalVoterCandidateDto;
        try {
            optionalVoterCandidateDto = voterCandidateService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalVoterCandidateDto.isPresent()) {
            return RESULT(optionalVoterCandidateDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!voterCandidateService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(voterCandidateService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(VoterCandidateDto voterCandidateDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(voterCandidateDto.getId())) {
                Optional<VoterCandidateDto> optionalVoterCandidateDto = voterCandidateService.findById(voterCandidateDto.getId());
                if (!optionalVoterCandidateDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
    }
}
