package com.ream.core.repository.security;

import com.ream.core.domain.security.TokenList;
import com.ream.core.domain.security.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenListRepository extends JpaRepository<TokenList, UUID> {


    List<TokenList> findByUsername(String username);

    void deleteByUsername(String username);

    void deleteByCreationDateTimeBefore(Instant createdDate);
}
