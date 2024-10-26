package com.ream.core.service.security.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessRoleDto {

    private Boolean admin;


    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    private Set<String> roleName;
    private Set<String> permissionKey;
    private Set<String> resourceKey;
    private Set<String> menuKey;

    public Set<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(Set<String> roleName) {
        this.roleName = roleName;
    }

    public Set<String> getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(Set<String> permissionKey) {
        this.permissionKey = permissionKey;
    }

    public Set<String> getResourceKey() {
        return resourceKey;
    }

    public void setResourceKey(Set<String> resourceKey) {
        this.resourceKey = resourceKey;
    }

    public Set<String> getMenuKey() {
        return menuKey;
    }

    public void setMenuKey(Set<String> menuKey) {
        this.menuKey = menuKey;
    }
}
