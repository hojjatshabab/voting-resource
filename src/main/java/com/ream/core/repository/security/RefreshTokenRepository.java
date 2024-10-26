package com.ream.core.repository.security;

import com.ream.core.domain.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.UUID;
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, UUID> {


    RefreshToken findByToken(String token);
    void deleteByUserId(UUID userId);

    void deleteByCreationDateTimeBefore(Instant createdDate);
}
