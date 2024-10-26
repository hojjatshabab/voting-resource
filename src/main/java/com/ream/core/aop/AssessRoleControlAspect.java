package com.ream.core.aop;

import com.ream.core.service.security.AccessService;
import com.ream.core.service.security.dto.AccessAddressSerializable;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.stream.Collectors;

@Aspect
@Component
public class AssessRoleControlAspect {


    @Autowired
    AccessService accessService;

    public AssessRoleControlAspect(AccessService accessService) {
        this.accessService = accessService;
    }

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *) " +
            "&& !( within(com.ream.core.controller.auth.PasswordController)|| within(com.ream.core.controller.auth.CaptchaController) || within(com.ream.core.controller.auth.AuthController)) "
    )
    public void restController() {
    }


    @Around("restController()")
    public Object aroundRestController(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        jakarta.servlet.http.HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String methodName = joinPoint.getSignature().getName();
        String requestPath = request.getRequestURI();
        String httpMethod = request.getMethod();
        String jwtToken = null;
        // اگر توکن در کوکی نبود، از هدر Authorization استفاده کنید
        if (jwtToken == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        if (jwtToken == null && Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    jwtToken = cookie.getValue();
                    break; // بلافاصله بعد از پیدا کردن کوکی درست خارج شوید
                }
            }
        }


        if (checkAccessRole(requestPath, httpMethod, jwtToken)) {
            // اگر دسترسی مجاز است، متد را اجرا کن
            return joinPoint.proceed();
        } else {
            // اگر دسترسی مجاز نیست، تنظیم پاسخ با کد وضعیت 403
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("Access denied to method: " + methodName + " with HTTP method: " + httpMethod + " on path: " + requestPath);
            response.getWriter().flush();
            return null;
        }


    }



    private boolean checkAccessRole(String path, String httpMethod, String token) {
        AccessAddressSerializable accessAddressRoleDtos = null;
        String[] parts = path.split("/");

        // اگر بخواهید قسمت آخر یعنی "asdas" را جدا کنید
        String firstPart = parts[1];
        String lastPart = parts[2];
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append("/").append(firstPart).append("/").append(lastPart);
        if (token != null) {
            accessAddressRoleDtos = accessService.getAccessAddress(token);
            if (Objects.nonNull(accessAddressRoleDtos))
                if (Objects.nonNull(accessAddressRoleDtos.getAdmin()) && !accessAddressRoleDtos.getAdmin())
                    if (accessAddressRoleDtos.getAccessAddressDtos().size() == 0) {
                        return false;
                    } else {
                        if (accessAddressRoleDtos.getAccessAddressDtos().stream().filter(f -> f.getMainUrl().equals(pathBuilder.toString())).collect(Collectors.toList()).size() == 0)
                            return false;
                    }

        }
        // پیاده‌سازی چک کردن دسترسی
        // در اینجا فرض می‌کنیم که دسترسی کاربر به متد در دیتابیس بررسی می‌شود
        // به صورت نمونه، دسترسی همیشه مجاز است
        return true; // منطق دسترسی را بر اساس نیاز خود پیاده‌سازی کنید
    }



}
