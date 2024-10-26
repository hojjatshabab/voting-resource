package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.voting.*;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Collection;
import java.util.Objects;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cd_common_base_data", schema = "public"
        , uniqueConstraints = {
        @UniqueConstraint(columnNames = {"value", "common_base_type_id"})})
public class Data extends AbstractAuditingEntity<Long> {
    @Column(name = "is_active")
    @Comment("jwt")
    private Boolean active;


    @Column(name = "value", nullable = false)
    @Comment("jwt")
    private String value;


    @Column(name = "description")
    @Comment("jwt")
    private String description;
    @Column(name = "key", nullable = false)
    @Comment("jwt")
    private String key;

    @Column(name = "order_no")
    @Comment("jwt")
    private Integer orderNo;

    @JoinColumn(name = "common_base_type_id", referencedColumnName = "id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private Type commonBaseType;


    @OneToMany(mappedBy = "commonBaseDataProvince")
    @JsonIgnore
    private Collection<City> cities;

    @OneToMany(mappedBy = "commonBaseDataFundamentalBase")
    @JsonIgnore
    private Collection<OrganizationUnit> organizationUnitsFundamentalBase;


    @OneToMany(mappedBy = "commonBaseDataUnitType")
    @JsonIgnore
    private Collection<OrganizationUnit> organizationUnitscommonBaseDataUnitType;


    @OneToMany(mappedBy = "commonBaseDataGeometricLocation")
    @JsonIgnore
    private Collection<OrganizationUnit> organizationUnitscommonBaseDataGeometricLocation;



    @OneToMany(mappedBy = "commonBaseDataProvince")
    @JsonIgnore
    private Collection<OrganizationUnit> organizationUnitscommonBaseDataProvince;


    public Collection<OrganizationUnit> getOrganizationUnitsFundamentalBase() {
        return organizationUnitsFundamentalBase;
    }

    public void setOrganizationUnitsFundamentalBase(Collection<OrganizationUnit> organizationUnitsFundamentalBase) {
        this.organizationUnitsFundamentalBase = organizationUnitsFundamentalBase;
    }

    public Collection<OrganizationUnit> getOrganizationUnitscommonBaseDataUnitType() {
        return organizationUnitscommonBaseDataUnitType;
    }

    public void setOrganizationUnitscommonBaseDataUnitType(Collection<OrganizationUnit> organizationUnitscommonBaseDataUnitType) {
        this.organizationUnitscommonBaseDataUnitType = organizationUnitscommonBaseDataUnitType;
    }

    public Collection<OrganizationUnit> getOrganizationUnitscommonBaseDataGeometricLocation() {
        return organizationUnitscommonBaseDataGeometricLocation;
    }

    public void setOrganizationUnitscommonBaseDataGeometricLocation(Collection<OrganizationUnit> organizationUnitscommonBaseDataGeometricLocation) {
        this.organizationUnitscommonBaseDataGeometricLocation = organizationUnitscommonBaseDataGeometricLocation;
    }

    public Collection<OrganizationUnit> getOrganizationUnitscommonBaseDataProvince() {
        return organizationUnitscommonBaseDataProvince;
    }

    public void setOrganizationUnitscommonBaseDataProvince(Collection<OrganizationUnit> organizationUnitscommonBaseDataProvince) {
        this.organizationUnitscommonBaseDataProvince = organizationUnitscommonBaseDataProvince;
    }

    public Collection<City> getCities() {
        return cities;
    }

    public void setCities(Collection<City> cities) {
        this.cities = cities;
    }


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

    public Type getCommonBaseType() {
        return commonBaseType;
    }

    public void setCommonBaseType(Type commonBaseType) {
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


