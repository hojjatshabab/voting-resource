package com.ream.core.service.security.impl;

import com.ream.core.config.JwtUtils;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.security.dto.UserDto;
import com.ream.core.domain.security.User;
import com.ream.core.service.security.mapper.UserDtoMapper;
import com.ream.core.repository.security.UserRepository;
import com.ream.core.service.security.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;


    @Autowired
    UserDtoMapper userDtoMapper;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;


    @Override
    public UserDto save(UserDto userDto) {
        //log.info("Save new common_base_data {} in to database.", cityDto.getName());
        userDto.setPassword(passwordEncoder.encode(userDto.getUsername().concat(userDto.getPassword())));

        return userDtoMapper.toDto(userRepository.save(userDtoMapper.toEntity(userDto)));
    }

    @Override
    public UserDto update(UserDto userDto) {
        if (Objects.isNull(userDto.getPassword())) {
            userDto.setPassword(userRepository.findById(userDto.getId()).get().getPassword());
        } else {
            userDto.setPassword(passwordEncoder.encode(userDto.getUsername().concat(userDto.getPassword())));
        }
//        log.info("Update common_base_data {} in to database.", cityDto.getName());
        return userDtoMapper.toDto(userRepository.save(userDtoMapper.toEntity(userDto)));

    }

    @Override
    public PageResponse<UserDto> findAll(PageRequest<UserDto> model) {
        List<UserDto> result = userRepository
                .findAll(
                        Pageable.ofSize(model.getPageSize())
                                .withPage(model.getCurrentPage() - 1))
                .stream().map(userDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = userRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<UserDto> findById(UUID id) {
//        log.info("Finding common_base_data by id {} .", id);
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return Optional.ofNullable(userDtoMapper.toDto(optionalUser.get()));
        }
        return Optional.empty();
    }


    @Override
    public Boolean deleteById(UUID id) {

        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find common_base_data by id {} .", id);
            return false;
        }
        userRepository.deleteById(id);
//        log.info("Success delete common_base_data by id {} .", id);
        return true;
    }

    @Override
    public Boolean updateALlPassword() {
        List<User> list = userRepository.findAll();
        int i = 1;
        for (User u : list) {
            if (!u.getUsername().equals("admin")) {
                System.out.println(i);
                u.setPassword(passwordEncoder.encode(u.getUsername().concat("A123456a!")));
                userRepository.save(u);
                i++;
            }
        }
        return true;
    }


    //***************************************


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.orElse(optionalUser.get());
    }


    @Override
    public UserDetails loadUserByUserId(UUID userId) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(userId);
        return optionalUser.orElse(optionalUser.get());
    }


    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDto getCurrentUser() {
        String jwtToken = extractJwtFromRequest(request);
        if (jwtToken != null) { // توجه کنید که در اینجا validateToken نیاز به UserDetails دارد
            String username = jwtUtils.getUsernameFromToken(jwtToken);
            Optional<User> optionalUser = userRepository.findByUsername(username);
            if (optionalUser.isPresent()) {
                return userDtoMapper.toDto(optionalUser.get());

            }
        }
        return null; // در صورتی که توکن معتبر نباشد یا کاربر پیدا نشود
    }


    @Override
    public UserDto getUserByUserName(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return userDtoMapper.toDto(optionalUser.get());

        }
        return null;
    }

    private String extractJwtFromRequest(HttpServletRequest request) {

        String bearerToken = null;
        bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        if (Objects.nonNull(request.getCookies())) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals("jwt")) {
                    bearerToken = cookie.getValue();
                    return bearerToken; // بلافاصله بعد از پیدا کردن کوکی درست خارج شوید
                }
            }
        }
        // اگر توکن در کوکی نبود، از هدر Authorization استفاده کنید

        return null;
    }
}
