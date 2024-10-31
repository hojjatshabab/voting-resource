package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "common_base_data", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"value", "common_base_type_id"})
})
public class CommonBaseData extends AbstractAuditingEntity<Long> {

    @Column(name = "is_active")
    @Comment("فعال / غیرفعال")
    private Boolean active;

    @Column(name = "value", nullable = false)
    @Comment("مقدار")
    private String value;

    @Column(name = "description")
    @Comment("توضیحات")
    private String description;

    @Column(name = "key", nullable = false)
    @Comment("کلید")
    private String key;

    @Column(name = "order_no")
    @Comment("ترتیب")
    private Integer orderNo;

    @JoinColumn(name = "common_base_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("نوع داده")
    private CommonBaseType commonBaseType;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public CommonBaseType getCommonBaseType() {
        return commonBaseType;
    }

    public void setCommonBaseType(CommonBaseType commonBaseType) {
        this.commonBaseType = commonBaseType;
    }

    @Override
    public void insertCreationAndModification() {
        if (Objects.isNull(this.active)) {
            active = true;
        }
        super.insertCreationAndModification();
    }

}


