package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "city", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"})
})
public class City extends AbstractAuditingEntity<UUID> {

    @Column(name = "name", nullable = false)
    @Comment("نام")
    private String name;

    @Column(name = "code", nullable = false)
    @Comment("کد")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "common_base_data_province", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @Comment("استان")
    private CommonBaseData commonBaseDataProvince;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CommonBaseData getCommonBaseDataProvince() {
        return commonBaseDataProvince;
    }

    public void setCommonBaseDataProvince(CommonBaseData commonBaseDataProvince) {
        this.commonBaseDataProvince = commonBaseDataProvince;
    }
}
