package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
//import com.ream.core.domain.baseInfo.OrganizationUnit;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Entity
@Table(name = "jw_user", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})})
public class User extends AbstractAuditingEntity<UUID> implements UserDetails {

    @Basic
    @Column(name = "username", nullable = false, unique = true)
    @Comment("username")
    private String username;
    @Basic
    @Column(name = "password", nullable = false, unique = true)
    @Comment("password")
    private String password;
    @Basic
    @Column(name = "first_name", nullable = true)
    @Comment("firstName")
    private String firstName;
    @Basic
    @Column(name = "last_name", nullable = true)
    @Comment("lastName")
    private String lastName;

    @Basic
    @Column(name = "enabled_user", nullable = false)
    @Comment("enabledUser")
    private Boolean enabledUser;

    @Basic
    @Column(name = "password_must_change", nullable = false)
    @Comment("enabledUser")
    private Boolean passwordMustChange;

    public Boolean getPasswordMustChange() {
        return passwordMustChange;
    }

    public void setPasswordMustChange(Boolean passwordMustChange) {
        this.passwordMustChange = passwordMustChange;
    }

    @Override
    public void insertCreationAndModification() {
        if (Objects.isNull(this.enabledUser)) {
            enabledUser = true;
        }

        super.insertCreationAndModification();
    }


    //    @ManyToOne
//    @JoinColumn(name = "organization_id", referencedColumnName = "id"
//            , insertable = false, updatable = false)
//    @Comment("organizationUnit")
//    private OrganizationUnit organizationUnit;
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    @Comment("UserRole")
    @JsonIgnore
    private List<UserRole> roleList;



    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getEnabledUser() {
        return enabledUser;
    }

    public void setEnabledUser(Boolean enabledUser) {
        this.enabledUser = enabledUser;
    }


//
//    public OrganizationUnit getOrganizationUnit() {
//        return organizationUnit;
//    }
//
//    public void setOrganizationUnit(OrganizationUnit organizationUnit) {
//        this.organizationUnit = organizationUnit;
//    }

    public List<UserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<UserRole> roleList) {
        this.roleList = roleList;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }


    //************************************************

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roleList.stream().map(r -> r.getRole()).toList())
            authorities.addAll(role.getAccesses());


        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if (this.enabledUser)
            return true;
        return false;
    }
}
