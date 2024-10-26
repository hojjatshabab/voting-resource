package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Collection;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cd_common_base_type", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"class_name"})})
public class Type extends AbstractAuditingEntity<Long> {

    @Basic
    @Column(name = "class_name", nullable = false,unique = true,updatable = false)
    @Comment("jwt")
    private String className;

    @OneToMany(mappedBy = "commonBaseType")
    @JsonIgnore
    private Collection<Data> commonDataById;

    @Column(name = "active")
    @Comment("jwt")
    private Boolean active;

    @Column(name = "persian_name", nullable = false)
    @Comment("jwt")
    private String title;


    @Column(name = "description")
    @Comment("jwt")
    private String description;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Collection<Data> getCommonDataById() {
        return commonDataById;
    }

    public void setCommonDataById(Collection<Data> commonDataById) {
        this.commonDataById = commonDataById;
    }
}
