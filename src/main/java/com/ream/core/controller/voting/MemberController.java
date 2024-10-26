package com.ream.core.controller.voting;

import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.model.Priority;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;

import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.SmsService;
import com.ream.core.service.voting.dto.MemberDto;
import com.sun.source.doctree.ErroneousTree;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/members")
@Tag(name = "اعضا", description = "اعضایی که قرار است انتخاب واحد انجام دهند")

public class MemberController extends BaseController {
    @Autowired
    MemberService memberService;


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

    public ActionResult<PageResponse<MemberDto>> findAll(@RequestParam(required = false) Map<String, String> params, Locale locale) {

        int currentPage = 0;
        int pageSize = 0;
        String nationalCodeStr = null;

        if (params.size() > 0) {
            currentPage = Integer.valueOf(params.get("currentPage"));
            pageSize = Integer.valueOf(params.get("pageSize"));
            nationalCodeStr = params.get("nationalCode");

        }

        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<MemberDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<MemberDto> MemberDtoPageResponse;
        try {
            MemberDtoPageResponse = memberService.findAllNationalCode(nationalCodeStr, request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(MemberDtoPageResponse)) {
            return NO_CONTENT("MemberDtoPageResponse", locale);
        } else {
            return RESULT(MemberDtoPageResponse, locale);
        }

    }


    @GetMapping("/id/{id}")
    public ActionResult<Optional<MemberDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<MemberDto> optionalMemberDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
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


    @GetMapping("/national-code/{nationalCode}")

    public ActionResult<Optional<List<MemberDto>>> findById(@PathVariable String nationalCode, Locale locale) {
        Optional<List<MemberDto>> optionalMemberDto;
        if (nationalCode.isEmpty()) {
            return NO_CONTENT(" nationalCode ", locale);
        }
        try {
            optionalMemberDto = memberService.findByNationalCodeContain(nationalCode);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalMemberDto.isPresent()) {
            return RESULT(optionalMemberDto, locale);
        } else {
            return NOT_FOUND(" optionalMemberDto ", locale);
        }
    }


    @Autowired
    LogHistoryService logHistoryService;
    @Autowired
    UserService userService;



    @GetMapping("/current-member")
    @Operation(summary = "لیست member بر اساس کاربر جاری ", description = "return ActionResult<Optional<List<MemberDto>>>")
    public ActionResult<Optional<List<MemberDto>>> findById(Locale locale) {
        Optional<List<MemberDto>> optionalMemberDto = Optional.empty();

        try {
            optionalMemberDto = memberService.getMembersByCurrentUser();
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(optionalMemberDto, locale);

    }


    @GetMapping("/current-member-remove-unit-select")
    @Operation(summary = "لیست member بر اساس کاربر جاری true میکند ", description = "return ActionResult<Boolean>")
    public ActionResult<Boolean> findRemoveUnitSelect(Locale locale) {
        Optional<List<MemberDto>> optionalMemberDto = Optional.empty();

        try {
            optionalMemberDto = memberService.getMembersByCurrentUser();
            if (optionalMemberDto.isPresent()) {
                for (MemberDto dto : optionalMemberDto.get()) {
                    dto.setUnitSelectionRemoved(true);
                    memberService.update(dto);
                }


            }
            return RESULT(true, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }


    }


    @GetMapping("/all-member/{memberId}")
    @Operation(summary = "لیست تمام اعضا یک قرارداد ", description = "return ActionResult<Optional<List<MemberDto>>>")
    public ActionResult<Optional<List<MemberDto>>> findAllMemberByMemberId(@PathVariable UUID memberId, Locale locale) {
        Optional<List<MemberDto>> optionalMemberDtos = Optional.empty();
        Optional<MemberDto> optionalMemberDto;
        if (Objects.isNull(memberId)) return NOT_FOUND(" memberId ", locale);
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                optionalMemberDtos = memberService.findAllMember(memberId);
            } else {
                return NOT_FOUND(" memberId ", locale);
            }
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        return RESULT(optionalMemberDtos.isPresent() ? Optional.ofNullable(optionalMemberDtos.get().stream().sorted(Comparator.comparing(MemberDto::getMaster, Comparator.reverseOrder())).collect(Collectors.toList())) : optionalMemberDtos, locale);
    }


    @GetMapping("/list")
    public ActionResult<List<MemberDto>> list(Locale locale) {
        try {
            return RESULT(memberService.findAll(), locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @Autowired
    private SmsService smsService;




    @GetMapping("/send-sms-confirm-member/{memberId}")
    @Operation(summary = "ارسال اس ام اس کد تایید بر اساس memberId", description = "ActionResult<Boolean> ")
    public ActionResult<Boolean> sendSmsConfirmByMemberId(@PathVariable UUID memberId, Locale locale) {
        if (Objects.isNull(memberId)) return NOT_FOUND(" memberId ", locale);
        Optional<MemberDto> optionalMemberDto;
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                if (optionalMemberDto.get().getMaster()) {
                    return RESULT(true, locale);
                } else {
                    String mobile = optionalMemberDto.get().getMobileNumber();
                    if (Objects.nonNull(mobile)) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("تایید انتخاب واحد").append("\n").append("شماره واحد قبلی: ").append(optionalMemberDto.get().getContractNumber() != null ? optionalMemberDto.get().getContractNumber() : " ")
                                .append("\n").append("کد تایید: ").append(optionalMemberDto.get().getConfirmCode());
                        smsService.sendMassages(optionalMemberDto.get().getNationalCode(), mobile, builder.toString());
                        return RESULT(true, locale);
                    }
                }
            }
            return RESULT(false, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }


    @GetMapping("/send-sms-confirm-member-code/{memberId}")
    @Operation(summary = "ارسال اس ام اس کد تایید بر اساس memberId", description = "ActionResult<Boolean> ")
    public ActionResult<String> sendSmsConfirmCodeByMemberId(@PathVariable UUID memberId, Locale locale) {
        if (Objects.isNull(memberId)) return NOT_FOUND(" memberId ", locale);
        Optional<MemberDto> optionalMemberDto;
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                if (optionalMemberDto.get().getMaster()) {
                    return RESULT(optionalMemberDto.get().getConfirmCode(), locale);
                } else {
                    String code = optionalMemberDto.get().getConfirmCode();
                    if (Objects.nonNull(code)) {
                        return RESULT(code, locale);
                    }
                }
            }
            return RESULT("", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }


    @GetMapping("/confirm-member-code/{memberId}")
    @Operation(summary = "ارسال اس ام اس کد تایید بر اساس memberId", description = "ActionResult<Boolean> ")
    public ActionResult<Boolean> confirmCodeByMemberId(@PathVariable UUID memberId, Locale locale) {
        if (Objects.isNull(memberId)) return NOT_FOUND(" memberId ", locale);
        Optional<MemberDto> optionalMemberDto;
        MemberDto memberDtoUpdate;
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                if (optionalMemberDto.get().getMaster()) {
                    return RESULT(true, locale);
                } else {
                    String code = optionalMemberDto.get().getConfirmCode();
                    if (Objects.nonNull(code)) {
                        optionalMemberDto.get().setConfirmSelection(true);
                        memberDtoUpdate = memberService.update(optionalMemberDto.get());
                        String mobile = optionalMemberDto.get().getMobileNumber();
                        if (Objects.nonNull(mobile)) {
                            StringBuilder builder = new StringBuilder();
                            builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("تایید انتخاب واحد با موفقیت انجام شد");
                            smsService.sendMassages(optionalMemberDto.get().getNationalCode(), mobile, builder.toString());
                        }
                        if (Objects.nonNull(memberDtoUpdate.getFinalityConfirm()) && memberDtoUpdate.getFinalityConfirm()) {
                            sendSmsToMaster(memberDtoUpdate);
                        }
                        return RESULT(true, locale);
                    }
                }
            }
            return RESULT(false, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }




    @GetMapping("/confirmation-sms-member")
    @Operation(summary = "تایید اس ام اس براساس member و code", description = "ActionResult<Boolean> ")
    public ActionResult<Boolean> confirmationSmsByMemberId(@RequestParam UUID memberId, @RequestParam String code, Locale locale) {
        if (Objects.isNull(memberId)) return NOT_FOUND(" memberId ", locale);
        if (Objects.isNull(code)) return NOT_FOUND(" code ", locale);
        if (code.isEmpty()) return NOT_FOUND(" code ", locale);

        Optional<MemberDto> optionalMemberDto;
        MemberDto memberDtoUpdate;
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                if (optionalMemberDto.get().getMaster()) {
                    return RESULT(true, locale);
                } else {
                    if (Objects.nonNull(optionalMemberDto.get().getConfirmCode())) {
                        if (optionalMemberDto.get().getConfirmCode().equals(code)) {
                            optionalMemberDto.get().setConfirmSelection(true);
                            memberDtoUpdate = memberService.update(optionalMemberDto.get());
                            String mobile = optionalMemberDto.get().getMobileNumber();
                            if (Objects.nonNull(mobile)) {
                                StringBuilder builder = new StringBuilder();
                                builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("تایید انتخاب واحد با موفقیت انجام شد");
                                smsService.sendMassages(optionalMemberDto.get().getNationalCode(), mobile, builder.toString());
                            }
                            if (Objects.nonNull(memberDtoUpdate.getFinalityConfirm()) && memberDtoUpdate.getFinalityConfirm()) {
                                sendSmsToMaster(memberDtoUpdate);
                            }
                            return RESULT(true, locale);
                        }
                    }

                }
            }
            return RESULT(false, locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }

    }

    private void sendSmsToMaster(MemberDto memberDto) {
        Optional<List<MemberDto>> optionalMemberDtos = Optional.empty();
        optionalMemberDtos = memberService.findAllMember(memberDto.getId());
        if (optionalMemberDtos.isPresent()) {
            optionalMemberDtos.get().stream().forEach(f -> {
                if (f.getMaster()) {
                    if (Objects.nonNull(f.getMobileNumber())) {
                        StringBuilder builder = new StringBuilder();
                        builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("شماره واحد قبلی: ").append(f.getContractNumber() != null ? f.getContractNumber() : " ").append("\n").append("تایید انتخاب واحد توسط تمام شرکا انجام گردید").append("\n").append("نماینده گرامی جهت تایید نهایی انتخاب واحد به سامانه مراجعه فرمایید.").append("\n").append("https://unit.betaja.ir");
                        smsService.sendMassages(f.getNationalCode(), f.getMobileNumber(), builder.toString());
                    }
                }
            });
        }

    }


    @GetMapping("/ok-unit-selection/{memberId}")
    @Operation(summary = "تایید تمام فرایند انتخاب واحد ها توسط وکیل memberId", description = "ActionResult<Boolean> ")
    public ActionResult<Boolean> sendSmsFinalityConfirm(@PathVariable UUID memberId, Locale locale) {
        Optional<MemberDto> optionalMemberDto;
        Optional<List<MemberDto>> optionalMemberDtos = Optional.empty();
        try {
            optionalMemberDto = memberService.findById(memberId);
            if (optionalMemberDto.isPresent()) {
                if (optionalMemberDto.get().getMaster() && optionalMemberDto.get().getFinalityConfirm()) {
                    optionalMemberDtos = memberService.findAllMember(memberId);
                    if (optionalMemberDtos.isPresent()) {
                        Random random = new Random();
                        Integer number = 10000000 + random.nextInt(90000000);
                        for (MemberDto d : optionalMemberDtos.get()) {
                            if (Objects.nonNull(d.getMobileNumber())) {
                                d.setIdCode(number.toString());
                                memberService.update(d);
                                StringBuilder builder = new StringBuilder();
                                builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append("انتخاب واحد شما تکمیل شد")
                                        .append("\n").append("شناسه انتخاب واحد : ").append(number.toString()).append("\n").append("**لطفا در حفظ و نگهداری این کد دقت فرمایید");
                                smsService.sendMassages(d.getNationalCode(), d.getMobileNumber(), builder.toString());
                            }
                        }
                        return RESULT(true, locale);
                    }
                }
            }
            return RESULT(false, locale);
        } catch (Exception e) {
            return RESULT(false, locale);
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
        if (Objects.isNull(memberDto.getMaster())) {
            NO_CONTENT(" getMaster ", locale);
        }
        if (Objects.isNull(memberDto.getPhoneNumber())) {
            NO_CONTENT(" getPhoneNumber ", locale);
        }
        if (Objects.isNull(memberDto.getNationalCode())) {
            NO_CONTENT(" getNationalCode ", locale);
        }

    }


}
