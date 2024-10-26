package com.ream.core.repository.voting;

import com.ream.core.domain.voting.SmsLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SmsLogRepository extends JpaRepository<SmsLog, UUID> {
    List<SmsLog> getByStatus(Integer status);

    SmsLog findTop1ByUserByOrderByCreationDateTimeDesc(String userBy);

}