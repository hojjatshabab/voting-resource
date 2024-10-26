package com.ream.core.repository.security;

import com.ream.core.domain.security.LogHistory;
import com.ream.core.domain.security.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Repository
public interface LogHistoryRepository extends JpaRepository<LogHistory, UUID> {

    List<LogHistory> findByUserNameAndActionNameOrderByCreationDateTimeDesc(String username, String actionName);

    List<LogHistory> findByActionNameAndCreationDateTimeAfterOrderByCreationDateTimeDesc(String actionName, Instant createdDate);
}
