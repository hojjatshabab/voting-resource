package com.ream.core.repository.security;

import com.ream.core.domain.security.Access;
import com.ream.core.domain.security.LogHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AccessRepository extends JpaRepository<Access, UUID> {

    List<Access> findByRoleId(UUID roleId);

    List<Access> findByIdIn(List<UUID> uuids);
}
