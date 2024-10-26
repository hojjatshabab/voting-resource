package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_role", schema = "public" , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_latin_name"})})
public class Role extends AbstractAuditingEntity<UUID> implements Serializable {

    @Basic
    @Column(name = "role_persian_name", nullable = false,unique = true)
    @Comment("rolePersianName")
    private String rolePersianName;
    @Basic
    @Column(name = "role_latin_name" , nullable = false,unique = true)
    @Comment("roleLatinName")
    private String roleLatinName;


    @Basic
    @Column(name = "max_user_binding" , nullable = true,unique = false)
    @Comment("maxUserBinding")
    private Long maxUserBinding;


    @OneToMany(mappedBy = "role", fetch = FetchType.EAGER)
    @Comment("permission")
    @JsonIgnore
    private List<Access> accesses;

    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }

    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<UserRole> userRoleList;


    public Long getMaxUserBinding() {
        return maxUserBinding;
    }

    public void setMaxUserBinding(Long maxUserBinding) {
        this.maxUserBinding = maxUserBinding;
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




    public List<UserRole> getUserRoleList() {
        return userRoleList;
    }

    public void setUserRoleList(List<UserRole> userRoleList) {
        this.userRoleList = userRoleList;
    }


}
