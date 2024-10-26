package com.ream.core.repository.security;

import com.ream.core.domain.security.User;
import com.ream.core.domain.security.UserRole;
import com.ream.core.service.security.dto.UserRoleDto;
import org.mapstruct.control.MappingControl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UUID> {
    List<UserRole> findByUser(User user);
}
