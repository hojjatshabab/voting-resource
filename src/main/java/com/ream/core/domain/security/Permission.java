package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.security.core.GrantedAuthority;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_permission", schema = "public"

)
public class Permission extends AbstractAuditingEntity<UUID> {

    @Column(name = "title", unique = true, nullable = false,length = 35)
    private String title;

    @Column(name = "key", unique = true, nullable = false,length = 100)
    private String key;

    @Column(name = "http_request_method", nullable = false)
    private String httpRequestMethod;

    @Column(name = "url", nullable = true)
    private String url;

    @OneToMany(mappedBy = "permission", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<ResourcePermission> resourcePermissionCollection;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getHttpRequestMethod() {
        return httpRequestMethod;
    }

    public void setHttpRequestMethod(String httpRequestMethod) {
        this.httpRequestMethod = httpRequestMethod;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Collection<ResourcePermission> getResourcePermissionCollection() {
        return resourcePermissionCollection;
    }

    public void setResourcePermissionCollection(Collection<ResourcePermission> resourcePermissionCollection) {
        this.resourcePermissionCollection = resourcePermissionCollection;
    }
}
