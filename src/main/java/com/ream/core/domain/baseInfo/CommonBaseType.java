package com.ream.core.domain.baseInfo;

import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "common_base_type", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"class_name"})
})
public class CommonBaseType extends AbstractAuditingEntity<Long> {

    @Basic
    @Column(name = "class_name", nullable = false, updatable = false)
    @Comment("نام کلاس")
    private String className;

    @Column(name = "active")
    @Comment("فعال / غیر فعال")
    private Boolean active;

    @Column(name = "title", nullable = false)
    @Comment("عنوان")
    private String title;

    @Column(name = "description")
    @Comment("توضیحات")
    private String description;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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
}
