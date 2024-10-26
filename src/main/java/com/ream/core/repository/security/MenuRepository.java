package com.ream.core.repository.security;

import com.ream.core.domain.security.Menu;
import com.ream.core.domain.security.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MenuRepository  extends JpaRepository<Menu, UUID>  {

}
