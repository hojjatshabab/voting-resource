package com.ream.core.service.security.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.security.ResourcePermission;
import com.ream.core.domain.security.Role;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccessDto {


    private UUID id;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID resourcePermissionId;

    private UUID roleId;

    public UUID getResourcePermissionId() {
        return resourcePermissionId;
    }

    public void setResourcePermissionId(UUID resourcePermissionId) {
        this.resourcePermissionId = resourcePermissionId;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }
}
