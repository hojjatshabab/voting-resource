package com.ream.core.config;

import com.ream.core.service.security.BlackListTokenService;
import com.ream.core.service.security.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {
    @Autowired
    private UserService jpaUserDetailsService;
    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private BlackListTokenService blackListTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String userEmail;
        String jwtToken = null;
        Boolean cokeis = false;


        // اگر توکن در کوکی نبود، از هدر Authorization استفاده کنید
        if (jwtToken == null && authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
        }

        if (jwtToken == null && Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    jwtToken = cookie.getValue();
                    jwtToken = Arrays.stream(jwtToken.split("@")).toList().get(0);
                    cokeis = true;
                    break; // بلافاصله بعد از پیدا کردن کوکی درست خارج شوید
                }
            }
        }

        // اگر توکن پیدا نشد، درخواست را ادامه دهید
        if (jwtToken == null) {
            filterChain.doFilter(request, response);
            return;
        }
        if (blackListTokenService.getByToken(jwtToken)) {
            filterChain.doFilter(request, response);
            return;
        }

//        jwtToken = authHeader.substring(7);
        Boolean expire = jwtUtils.isTokenExpired(jwtToken);
///کوکی فرانت اوکی شد باید برداشته شود
        if (cokeis && Objects.nonNull(expire) && expire) {
            filterChain.doFilter(request, response);
            return;
        }
        ///کوکی فرانت اوکی شد باید برداشته شود

        if (Objects.nonNull(expire) && expire) {
            response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value()); // تنظیم وضعیت HTTP به 422
            response.getWriter().write("Token has expired"); // نوشتن پیام خطا
            return; // ادامه فیلتر را متوقف کنید}
        }
        if (Objects.isNull(expire)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value()); // تنظیم وضعیت HTTP به 401
            response.getWriter().write("Token has inValid"); // نوشتن پیام خطا
            return; // ادامه فیلتر را متوقف کنید}
        }

        userEmail = jwtUtils.extractUsername(jwtToken);
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(userEmail);
            if (jwtUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
