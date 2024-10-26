package com.ream.core.repository.security;

import com.ream.core.domain.security.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ResourceRepository  extends JpaRepository<Resource, UUID> {
}
