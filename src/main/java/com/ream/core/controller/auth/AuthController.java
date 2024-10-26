package com.ream.core.controller.auth;


import com.ream.core.controller.ActionResult;
import com.ream.core.controller.BaseController;
import com.ream.core.config.JwtUtils;
import com.ream.core.domain.security.RefreshToken;
import com.ream.core.domain.security.User;
import com.ream.core.model.Priority;
import com.ream.core.service.JalaliCalendar;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.SmsService;
import com.ream.core.service.security.*;
import com.ream.core.service.security.dto.UserDto;
import com.ream.core.service.voting.dto.MemberDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "کلاس public برای دریافت token", description = "حتما درخواست از نوع AuthenticationRequest باشد و پاسخ از نوع AuthenticationResponse است. ")
public class AuthController extends BaseController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService jpaUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private CaptchaController captchaController;


    @Autowired
    private LogHistoryService logHistoryService;

    @Autowired
    private TokenListService tokenListService;

    @Autowired
    private UserRoleService userRoleService;

    @Value("${jwt.expiration}")

    private Long expireToken;


    @Value("${allow-origin}")
    private String allowOrigin;

    public String getAllowOrigin() {
        return allowOrigin;
    }

    public void setAllowOrigin(String allowOrigin) {
        this.allowOrigin = allowOrigin;
    }

    public Long getExpireToken() {
        return expireToken;
    }

    public void setExpireToken(Long expireToken) {
        this.expireToken = expireToken;
    }

    @PostMapping("/authenticate")
    @Operation(summary = "احراز هویت", description = "Object")
    public Object authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response, Locale locale) {
        try {
            if (Objects.isNull(request.getCaptcha()))
                return PAYMENT_REQUIRED("Error authenticating", locale);
            if (!captchaController.verifyCaptcha(request.getCaptcha(), locale))
                return new ResponseStatusException(HttpStatus.PAYMENT_REQUIRED, "captcha");
            if (Objects.isNull(request.getUsername()) || Objects.isNull(request.getPassword())) {
                logHistoryService.setLog("know", "LOGIN-ERROR", "خطا در ورود کاربر و خالی بودن کاربری", Priority.VERY_HIGH);
                return UNAUTHORIZED("Error authenticating", locale);
            }

            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getUsername().concat(request.getPassword()),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getUsername());
            if (user != null) {
                if (!userRoleService.findByUserId(((User) user).getId()).isPresent()) {
                    logHistoryService.setLog(request.getUsername(), "LOGIN-ERROR-ROLE", "کاربر جاری نقش ندارد", Priority.VERY_HIGH);
                    return new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, "User is not Role");
                }
                String refreshToken = refreshTokenService.createRefreshToken(((User) user).getId());
                String jwt = jwtUtils.generateToken(user);
                Cookie cookie = new Cookie("jwt", jwt.concat("@").concat(refreshToken));
                cookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 days
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);


                try {
                    Optional<MemberDto> memberDto = null /*memberService.findByNationalCode(((User) user).getUsername())*/;
                    if (memberDto.isPresent()) {
                        String mobile = memberDto.get().getMobileNumber();
                        if (Objects.nonNull(mobile)) {
                            JalaliCalendar jalaliCalendar = new JalaliCalendar(new Date());
                            StringBuilder builder = new StringBuilder();
                            builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append(" ورود به سامانه ").append("\n").append(jalaliCalendar.toString()).append(" ").append(jalaliCalendar.getTime());
                            smsService.sendMassages(user.getUsername(), mobile, builder.toString());
                        }
                    }
                } catch (Exception e) {
                }
                tokenListService.save(user.getUsername(), jwt);
                logHistoryService.setLog(request.getUsername(), "LOGIN-SUCCESSFUL", "ورود کاربر", Priority.HIGH);
                if (((User) user).getPasswordMustChange()) {
                    return new AuthenticationResponse(jwt, expireToken, 201, refreshToken);
                }

                return new AuthenticationResponse(jwt, expireToken, 200, refreshToken);
            }
            logHistoryService.setLog(request.getUsername(), "LOGIN-ERROR", "خطلا در ورود کاربر و نبودن کاربر جز کاربران", Priority.VERY_HIGH);

            return UNAUTHORIZED("Error authenticating", locale);
        } catch (Exception e) {
            if (e.getMessage().contains("User is disabled")) {
                logHistoryService.setLog(request.getUsername(), "LOGIN-ERROR", "تلاش برای ورود کاربر غیر فعال", Priority.VERY_HIGH);
                return new ResponseStatusException(HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE, "User is disabled");
            }
            logHistoryService.setLog(request.getUsername(), "LOGIN-ERROR", "خطا در ورود کاربر", Priority.VERY_HIGH);
            return UNAUTHORIZED("Error authenticating", locale);
        }
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.OPTIONS)
    public ResponseEntity optionsGetCaptcha(HttpServletResponse response) throws IOException {
        response.addHeader("Access-Control-Allow-Origin", allowOrigin);
        response.addHeader("Access-Control-Allow-Headers", "content-type, Y2FwdGNoYQ");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        return new ResponseEntity(HttpStatus.OK);
    }


    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/refresh-token")
    public Object refreshToken(@RequestParam String refreshToken, HttpServletResponse response, Locale locale) {
        RefreshToken validatedToken = refreshTokenService.validateRefreshToken(refreshToken);
        if (validatedToken != null) {
            // Assuming you have a method to get user details by ID
            final UserDetails userDetails = jpaUserDetailsService.loadUserByUserId(validatedToken.getUserId());
            if (Objects.nonNull(userDetails)) {
                String refreshTokenSaved = refreshTokenService.createRefreshToken(((User) userDetails).getId());
                String jwt = jwtUtils.generateToken(userDetails);
                Cookie cookie = new Cookie("jwt", jwt.concat("@").concat(refreshTokenSaved));
                cookie.setMaxAge(1 * 24 * 60 * 60); // expires in 1 days
                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);
                tokenListService.save(userDetails.getUsername(), jwt);
                logHistoryService.setLog(userDetails.getUsername(), "LOGIN-REFRESH-TOKEN", "ورود کاربر با رفرش توکن", Priority.HIGH);
                return new AuthenticationResponse(jwt, expireToken, 200,refreshTokenSaved);
            }
        }
        return UNAUTHORIZED("Error authenticating", locale);


    }
    @Autowired
    private BlackListTokenService blackListTokenService;

    @Autowired
    private SmsService smsService;


    @Autowired
    private MemberService memberService;

    @PostMapping("/password-ignore")
    @Operation(summary = "فراموشی رمز", description = " ActionResult<String>")
    public ActionResult<String> passwordIgnore(@RequestParam String userName, Locale locale) {
        try {


            if (Objects.isNull(userName) || userName.trim().isEmpty() || userName.trim().isBlank()) {
                logHistoryService.setLog("know", "PASSWORD-IGNORE-ERROR", "خطا تغییر پسورد", Priority.VERY_HIGH);
                return RESULT(" خطا در شناسه کاربری !!", locale);
            }
            final UserDto userDto = jpaUserDetailsService.getUserByUserName(userName.trim());
            if (Objects.nonNull(userDto)) {
                Optional<MemberDto> memberDto = null /*memberService.findByNationalCode(userDto.getUsername())*/;
                String mobile = null;
                if (memberDto.isPresent()) {
                    mobile = memberDto.get().getMobileNumber();
                }
                if (Objects.isNull(mobile)) {
                    logHistoryService.setLog(userName, "PASSWORD-IGNORE-CONFLICT", " تلفن برای تغییر پسورد یافت نشد", Priority.VERY_HIGH);

                    return RESULT(" تلفن همراه کاربر ثبت نشده است !!", locale);
                }
                Random random = new Random();
                Integer number = 10000000 + random.nextInt(90000000);
                userDto.setPassword(number.toString());
                userDto.setPasswordMustChange(true);
                jpaUserDetailsService.update(userDto);
                StringBuilder builder = new StringBuilder();
                builder.append("*سامانه سرا بنیاد تعاون آجا*").append("\n").append(" رمز عبور موقت : ").append(number);
                smsService.sendMassages(userDto.getUsername(), mobile, builder.toString());
                String maskedPhone = maskMobileNumber(mobile);
                StringBuilder maskedMassage = new StringBuilder();
                maskedMassage.append(" پیامک رمز به شماره ").append(maskedPhone.toString()).append(" ارسال شد ");
                logHistoryService.setLog(userName, "PASSWORD-IGNORE-SUCCESSFUL", "تغییر پسورد", Priority.HIGH);
                return RESULT(maskedMassage.toString(), locale);
            } else {
                logHistoryService.setLog(userName, "PASSWORD-IGNORE-NOTFOUND", "خطا تغییر پسورد و پیدا نکردن کاربر", Priority.VERY_HIGH);
                return RESULT(" شناسه کاربری موجود نیست !!", locale);
            }
        } catch (Exception e) {
            logHistoryService.setLog(userName, "PASSWORD-IGNORE-ERROR", "خطا تغییر پسورد", Priority.VERY_HIGH);
            return RESULT("خطا در سیستم !!", locale);
        }

    }


    private String maskMobileNumber(String mobile) {
        // فرض بر این است که شماره تلفن 11 رقمی است
        if (mobile.trim().length() == 11) {
            String lastFour = mobile.trim().substring(8, 11); // آخرین 4 رقم
            String firstPart = mobile.trim().substring(0, 4); // باقی‌مانده شماره

            // برای شماره تلفن‌های 11 رقمی و بیشتر

            // ترکیب بخش‌های ماسک شده و غیرماسک شده
            return lastFour + "****" + firstPart;
        }
        return mobile;
    }


    @GetMapping("/logout")
    public ActionResult<String> logout(HttpServletRequest request, Locale locale) {
        String token = null;
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }

        if (token == null && Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    token = cookie.getValue();
                    break; // بلافاصله بعد از پیدا کردن کوکی درست خارج شوید
                }
            }
        }
        if (token != null) {
            blackListTokenService.save(token);
        }
        UserDto userDto = jpaUserDetailsService.getCurrentUser();
        if (Objects.nonNull(userDto)) {
            refreshTokenService.deleteRefreshToken(userDto.getId());
            tokenListService.logOutUserByUserName(userDto.getUsername());
        }
        logHistoryService.setLog(Objects.nonNull(userDto) ? userDto.getUsername() : "know", "LOGOUT-SUCCESSFUL", "خروج کاربر", Priority.HIGH);
        return RESULT("Logged out successfully", locale);
    }


}
