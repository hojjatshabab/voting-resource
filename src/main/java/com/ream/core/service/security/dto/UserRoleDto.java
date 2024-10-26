package com.ream.core.service.security.dto;

import com.ream.core.domain.security.Role;
import com.ream.core.domain.security.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UserRoleDto {

    private UUID id;


    private String roleRolePersianName;

    private String roleRoleLatinName;


    private String userUsername;

    public String getRoleRolePersianName() {
        return roleRolePersianName;
    }

    public void setRoleRolePersianName(String roleRolePersianName) {
        this.roleRolePersianName = roleRolePersianName;
    }

    public String getRoleRoleLatinName() {
        return roleRoleLatinName;
    }

    public void setRoleRoleLatinName(String roleRoleLatinName) {
        this.roleRoleLatinName = roleRoleLatinName;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    private UUID roleId;

    private UUID userId;


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getRoleId() {
        return roleId;
    }

    public void setRoleId(UUID roleId) {
        this.roleId = roleId;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
}
