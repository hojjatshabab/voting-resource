package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "jw_access", schema = "public" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"resource_permission_id", "role_id"})} )
public class Access extends AbstractAuditingEntity<UUID> implements Serializable, GrantedAuthority {

    @JoinColumn(name = "resource_permission_id", referencedColumnName = "id",insertable = true,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private ResourcePermission resourcePermission;

    @JoinColumn(name = "role_id", referencedColumnName = "id",insertable = true,updatable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Role role;

    @Override
    public String getAuthority() {
        return this.getId().toString();
    }


    public ResourcePermission getResourcePermission() {
        return resourcePermission;
    }

    public void setResourcePermission(ResourcePermission resourcePermission) {
        this.resourcePermission = resourcePermission;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
