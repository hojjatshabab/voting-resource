package com.ream.core.service.security;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.CityDto;
import com.ream.core.service.security.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;
import java.util.UUID;

public interface UserService extends UserDetailsService {
    UserDto save(UserDto userDto);

    UserDto update(UserDto userDto);

    PageResponse<UserDto> findAll(PageRequest<UserDto> model);

    Optional<UserDto> findById(UUID id);

    Boolean deleteById(UUID id);

    Boolean updateALlPassword();


    UserDetails loadUserByUsername(String username);

    UserDetails loadUserByUserId(UUID userId);

    UserDto getCurrentUser();

    UserDto getUserByUserName(String username);

}
