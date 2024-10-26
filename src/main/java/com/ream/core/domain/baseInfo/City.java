package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Collection;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ba_city", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code"})
}
)
public class City extends AbstractAuditingEntity<UUID> {

    @Column(name = "name", nullable = false)

    @Comment("jwt")
    private String name;

    @Column(name = "code", nullable = false)

    @Comment("jwt")
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "common_base_data_province", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    @Comment("jwt")
    private com.ream.core.domain.baseInfo.Data commonBaseDataProvince;

    @OneToMany(mappedBy = "city")
    @JsonIgnore
    private Collection<OrganizationUnit> organizationUnits;

    public Collection<OrganizationUnit> getOrganizationUnits() {
        return organizationUnits;
    }

    public void setOrganizationUnits(Collection<OrganizationUnit> organizationUnits) {
        this.organizationUnits = organizationUnits;
    }

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

    public com.ream.core.domain.baseInfo.Data getCommonBaseDataProvince() {
        return commonBaseDataProvince;
    }

    public void setCommonBaseDataProvince(com.ream.core.domain.baseInfo.Data commonBaseDataProvince) {
        this.commonBaseDataProvince = commonBaseDataProvince;
    }
}
