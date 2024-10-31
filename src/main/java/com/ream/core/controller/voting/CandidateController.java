package com.ream.core.controller.voting;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.CandidateService;
import com.ream.core.service.voting.VoterService;
import com.ream.core.service.voting.dto.CandidateDto;
import com.ream.core.service.voting.dto.VoterDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/candidates")
@Tag(name = "نمایندگان", description = "کسانی که نماینده هستند.")
public class CandidateController extends BaseController {
    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @PostMapping
    public ActionResult<CandidateDto> save(@RequestBody CandidateDto candidateDto, Locale locale) {
        isExist(candidateDto, ModeType.CREATE, locale);
        try {
            return RESULT(candidateService.save(candidateDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<CandidateDto> update(@RequestBody CandidateDto candidateDto, Locale locale) {
        isExist(candidateDto, ModeType.EDIT, locale);
        try {
            return RESULT(candidateService.update(candidateDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<CandidateDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<CandidateDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<CandidateDto> candidateDtoPageResponse;
        try {
            candidateDtoPageResponse = candidateService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(candidateDtoPageResponse)) {
            return NO_CONTENT(" candidateDtoPageResponse ", locale);
        } else {
            return RESULT(candidateDtoPageResponse, locale);
        }
    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<CandidateDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<CandidateDto> optionalCandidateDto;
        try {
            optionalCandidateDto = candidateService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalCandidateDto.isPresent()) {
            return RESULT(optionalCandidateDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!candidateService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(candidateService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(CandidateDto candidateDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(candidateDto.getId())) {
                Optional<CandidateDto> optionalCandidateDto = candidateService.findById(candidateDto.getId());
                if (!optionalCandidateDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
    }
}
