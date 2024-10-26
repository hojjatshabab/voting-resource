package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;

import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_user_role", schema = "public",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"role_id","user_id"})})
public class UserRole extends AbstractAuditingEntity<UUID> implements GrantedAuthority {


    @ManyToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id",insertable = true,updatable = false)
    @Comment("role")
    private Role role;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id",insertable = true,updatable = false)
    @Comment("user")
    private User user;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //*********************************************************


    @Override
    public String getAuthority() {
        return this.getRole().getRoleLatinName();
    }
}
