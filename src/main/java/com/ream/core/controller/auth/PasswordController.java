package com.ream.core.controller.auth;

import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.model.Priority;
import com.ream.core.service.JalaliCalendar;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.SmsService;
import com.ream.core.service.security.LogHistoryService;
import com.ream.core.service.security.UserService;
import com.ream.core.service.security.dto.UserDto;
import com.ream.core.service.voting.dto.MemberDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("api/password")
@Tag(name = "کلاس public برای دریافت password و تغییرات آن", description = "برای زمانی است که یوزر باید عوض شود")

public class PasswordController extends BaseController {
    @Autowired
    private UserService jpaUserDetailsService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private SmsService smsService;

    @Autowired
    private LogHistoryService logHistoryService;

    @PostMapping("/change-password")
    public ActionResult<Boolean> changePassword(@RequestBody PasswordRequest request, Locale locale) {


        try {

            UserDto userDto = jpaUserDetailsService.getCurrentUser();
            if (Objects.isNull(request.getPassword()) || Objects.isNull(request.getSecondPassword())){

                logHistoryService.setLog(Objects.nonNull(userDto) ? userDto.getUsername() : "know", "CHANGE-PASSWORD_CONFLICT", "تغییر رمز باخطا مواجه شد بدلیل اینکه رمز با رمز دوم یکی نیست", Priority.VERY_HIGH);

                return NOT_FOUND("password,secondPassword", locale);
            }
            if (request.getPassword().equals(request.getSecondPassword())) {
                UserDto dto = jpaUserDetailsService.getCurrentUser();
                if (Objects.nonNull(dto)) {
                    dto.setPassword(request.getPassword());
                    dto.setPasswordMustChange(false);
                    jpaUserDetailsService.update(dto);
                    try {
                        Optional<MemberDto> memberDto =null /* memberService.findByNationalCode(dto.getUsername())*/;
                        if (memberDto.isPresent()) {
                            String mobile = memberDto.get().getMobileNumber();
                            if (Objects.nonNull(mobile)) {
                                JalaliCalendar jalaliCalendar = new JalaliCalendar(new Date());
                                StringBuilder builder = new StringBuilder();
                                builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append(" تغییر رمز با موفقیت انجام شد ").append("\n").append(jalaliCalendar.toString()).append(" ").append(jalaliCalendar.getTime()).append("\n")
                                        .append("کاربری :").append(userDto.getUsername());
                                smsService.sendMassages(dto.getUsername(), mobile, builder.toString());
                            }
                        }
                    } catch (Exception e) {
                    }

                    logHistoryService.setLog(Objects.nonNull(userDto) ? userDto.getUsername() : "know", "CHANGE-PASSWORD-SUCCESSFUL", "تغییر رمز با موفقیت انجام شد", Priority.HIGH);

                    return RESULT(true, locale);
                }
            }
        } catch (Exception e) {
            UserDto userDto = jpaUserDetailsService.getCurrentUser();
            logHistoryService.setLog(Objects.nonNull(userDto) ? userDto.getUsername() : "know", "CHANGE-PASSWORD-ERROR", "تغییر رمز با خطا مواجه شد", Priority.VERY_HIGH);
            return INTERNAL_SERVER_ERROR("change-password", locale);
        }

        return RESULT(false, locale);
    }
}
