package com.ream.core.controller.voting;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.VoterService;
import com.ream.core.service.voting.VotingService;
import com.ream.core.service.voting.dto.VoterDto;
import com.ream.core.service.voting.dto.VotingDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("api/voting")
@Tag(name = "فرایند رای گیری", description = "فرآیند رای گیری را مشخص میکند.")
public class VotingController extends BaseController {
    private final VotingService votingService;

    public VotingController(VotingService votingService) {
        this.votingService = votingService;
    }

    @PostMapping
    public ActionResult<VotingDto> save(@RequestBody VotingDto votingDto, Locale locale) {
        isExist(votingDto, ModeType.CREATE, locale);
        try {
            return RESULT(votingService.save(votingDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<VotingDto> update(@RequestBody VotingDto votingDto, Locale locale) {
        isExist(votingDto, ModeType.EDIT, locale);
        try {
            return RESULT(votingService.update(votingDto), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<VotingDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<VotingDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<VotingDto> votingDtoPageResponse;
        try {
            votingDtoPageResponse = votingService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(votingDtoPageResponse)) {
            return NO_CONTENT(" votingDtoPageResponse ", locale);
        } else {
            return RESULT(votingDtoPageResponse, locale);
        }
    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<VotingDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<VotingDto> optionalVotingDto;
        try {
            optionalVotingDto = votingService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalVotingDto.isPresent()) {
            return RESULT(optionalVotingDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!votingService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(votingService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(VotingDto votingDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(votingDto.getId())) {
                Optional<VotingDto> optionalVotingDto = votingService.findById(votingDto.getId());
                if (!optionalVotingDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
    }
}
