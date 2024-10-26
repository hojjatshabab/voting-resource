package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@Table(name = "jw_resource_permission", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"permission_id", "resource_id"})})
public class ResourcePermission extends AbstractAuditingEntity<UUID> {

    @JoinColumn(name = "permission_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Permission permission;

    @JoinColumn(name = "resource_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Resource resource;

    @OneToMany(mappedBy = "resourcePermission", fetch = FetchType.EAGER)
    @Comment("accesses")
    @JsonIgnore
    private List<Access> accesses;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Access> getAccesses() {
        return accesses;
    }

    public void setAccesses(List<Access> accesses) {
        this.accesses = accesses;
    }


}