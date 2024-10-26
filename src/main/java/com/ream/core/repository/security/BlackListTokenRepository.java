package com.ream.core.repository.security;

import com.ream.core.domain.security.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BlackListTokenRepository extends JpaRepository<BlackListToken, UUID> {

    Optional<List<BlackListToken>> getByToken(String token);

    void deleteByCreationDateTimeBefore(Instant createdDate);

}
