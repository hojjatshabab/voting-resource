package com.ream.core.controller.voting;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.dto.MemberDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/members")
@Tag(name = "اعضا", description = "اعضای کلی تشکیل دهنده یک سازمان می باشد.")
public class MemberController extends BaseController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ActionResult<MemberDto> save(@RequestBody MemberDto memberDto, Locale locale) {
        isExist(memberDto, ModeType.CREATE, locale);
        try {
            return RESULT(memberService.save(memberDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT(" national_code ", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @PutMapping
    public ActionResult<MemberDto> update(@RequestBody MemberDto memberDto, Locale locale) {
        isExist(memberDto, ModeType.EDIT, locale);
        try {
            return RESULT(memberService.update(memberDto), locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("national_code", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    public ActionResult<PageResponse<MemberDto>> findAll(@RequestParam int currentPage, @RequestParam int pageSize, Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<MemberDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<MemberDto> memberDtoPageResponse;
        try {
            memberDtoPageResponse = memberService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(memberDtoPageResponse)) {
            return NO_CONTENT(" memberDtoPageResponse ", locale);
        } else {
            return RESULT(memberDtoPageResponse, locale);
        }
    }

    @GetMapping("/id/{id}")
    public ActionResult<Optional<MemberDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<MemberDto> optionalMemberDto;
        try {
            optionalMemberDto = memberService.findById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalMemberDto.isPresent()) {
            return RESULT(optionalMemberDto, locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }

    @GetMapping("/list")
    public ActionResult<List<MemberDto>> list(Locale locale) {
        try {
            return RESULT(memberService.findAll(), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @DeleteMapping("/{id}")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!memberService.findById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(memberService.deleteById(id), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    private void isExist(MemberDto memberDto, ModeType modeType, Locale locale) {
        if (modeType.equals(ModeType.EDIT)) {
            if (Objects.nonNull(memberDto.getId())) {
                Optional<MemberDto> optionalCityDto = memberService.findById(memberDto.getId());
                if (!optionalCityDto.isPresent()) {
                    NOT_FOUND(" id ", locale);
                }
            } else {
                NO_CONTENT(" id ", locale);
            }
        }
        if (Objects.isNull(memberDto.getFirstName())) {
            NO_CONTENT(" getFirstName ", locale);
        }
        if (Objects.isNull(memberDto.getLastName())) {
            NO_CONTENT(" getLastName ", locale);
        }
        if (Objects.isNull(memberDto.getFatherName())) {
            NO_CONTENT(" getFatherName ", locale);
        }
        if (Objects.isNull(memberDto.getPhoneNumber())) {
            NO_CONTENT(" getPhoneNumber ", locale);
        }
        if (Objects.isNull(memberDto.getNationalCode())) {
            NO_CONTENT(" getNationalCode ", locale);
        }
    }
}
