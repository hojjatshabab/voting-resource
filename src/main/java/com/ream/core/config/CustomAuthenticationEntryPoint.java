package com.ream.core.config;
import com.ream.core.model.Priority;
import com.ream.core.service.security.LogHistoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private LogHistoryService logHistoryService;
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        logHistoryService.setLog("know", "UNAUTHORIZED", "عدم دسترسی به دلیل نداشتن token", Priority.VERY_HIGH);

        // تنظیم کد وضعیت 401 برای درخواست‌های بدون احراز هویت معتبر
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}