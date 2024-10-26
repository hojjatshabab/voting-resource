package com.ream.core.repository.security;

import com.ream.core.domain.security.Permission;
import com.ream.core.domain.security.ResourcePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
