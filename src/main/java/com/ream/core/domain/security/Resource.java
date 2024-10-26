package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_resource", schema = "public"
)
public class Resource extends AbstractAuditingEntity<UUID> {

    @Column(name = "title", unique = true, nullable = false,length = 35)

    private String title;

    @Column(name = "key", unique = true, nullable = false,length = 100)

    private String key;

    @Column(name = "url", nullable = false)
    private String url;

    @JoinColumn(name = "menu_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Menu menu;


    @OneToMany(mappedBy = "resource", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<ResourcePermission> resourcePermissionCollection;


    public Collection<ResourcePermission> getResourcePermissionCollection() {
        return resourcePermissionCollection;
    }

    public void setResourcePermissionCollection(Collection<ResourcePermission> resourcePermissionCollection) {
        this.resourcePermissionCollection = resourcePermissionCollection;
    }

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}