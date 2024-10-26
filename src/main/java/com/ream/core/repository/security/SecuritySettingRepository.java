package com.ream.core.repository.security;

import com.ream.core.domain.security.SecuritySetting;
import com.ream.core.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SecuritySettingRepository extends JpaRepository<SecuritySetting, UUID> {
}
