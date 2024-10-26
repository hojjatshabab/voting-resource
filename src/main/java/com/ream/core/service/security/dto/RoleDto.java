package com.ream.core.service.security.dto;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {


    private UUID id;
    private String rolePersianName;

    private String roleLatinName;

    private Long maxUserBinding;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getRolePersianName() {
        return rolePersianName;
    }

    public void setRolePersianName(String rolePersianName) {
        this.rolePersianName = rolePersianName;
    }

    public String getRoleLatinName() {
        return roleLatinName;
    }

    public void setRoleLatinName(String roleLatinName) {
        this.roleLatinName = roleLatinName;
    }

    public Long getMaxUserBinding() {
        return maxUserBinding;
    }

    public void setMaxUserBinding(Long maxUserBinding) {
        this.maxUserBinding = maxUserBinding;
    }
}
