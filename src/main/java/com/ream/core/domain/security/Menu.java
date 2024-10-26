package com.ream.core.domain.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.baseInfo.OrganizationUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.UUID;

@lombok.Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_menu", schema = "public"
)
public class Menu extends AbstractAuditingEntity<UUID> {

    @Column(name = "title", unique = true, nullable = false,length = 35)
    private String title;

    @Column(name = "key", unique = true, nullable = false,length = 100)
    private String key;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("سازمان پدر")
    private Menu parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Menu> menus;

    @OneToMany(mappedBy = "menu", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<Resource> resourceCollection;

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

    public Menu getParent() {
        return parent;
    }

    public void setParent(Menu parent) {
        this.parent = parent;
    }

    public Collection<Menu> getMenus() {
        return menus;
    }

    public void setMenus(Collection<Menu> menus) {
        this.menus = menus;
    }

    public Collection<Resource> getResourceCollection() {
        return resourceCollection;
    }

    public void setResourceCollection(Collection<Resource> resourceCollection) {
        this.resourceCollection = resourceCollection;
    }

}