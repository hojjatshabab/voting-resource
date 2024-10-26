package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.Comment;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "or_organization_unit", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code_path"}),
        @UniqueConstraint(columnNames = {"code"})
})
public class OrganizationUnit extends AbstractAuditingEntity<UUID> {
    @Column(name = "name" , nullable = false)
    @Comment("jwt")
    private String name;
    @Column(name = "address"  )
    @Comment("jwt")
    private String address;
    @Column(name = "server_ip" )
    @Comment("jwt")
    private String serverAddress;
    @Column(name = "email"  )
    @Comment("jwt")
    private String email;
    @Column(name = "complete_name"  )
    @Comment("jwt")
    private String completeName;
    @Column(name = "tell_number"  )
    @Comment("jwt")
    private String tellNumber;
    @Column(name = "code"  , nullable = false)
    @Comment("jwt")
    private String code;
    @Column(name = "active", nullable = false)
    @Comment("jwt")
    private Boolean active;
    @Column(name = "code_path"  , nullable = false)
    @Comment("jwt")
    private String codePath;
    @Column(name = "priority"  )
    @Comment("jwt")
    private Integer priority;
    @Column(name = "start_date", nullable = true)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date startExpireDate;
    @Column(name = "expire_date", nullable = true)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date endExpireDate;
    @Column(name = "justification_date", nullable = true)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date dateAuthorizationLetter;
    @Column(name = "authorization_letter_number"  )
    @Comment("jwt")
    private String authorizationLetterNumber;
    @Comment("jwt")
    @Column(name = "geometric_location_x")
    private Double geometricLocation_x;

    @Column(name = "geometric_location_y")
    @Comment("jwt")
    private Double geometricLocation_y;
    @Column(name = "work_org_capacity")
    @Comment("jwt")
    private Integer workOrgCapacity;
    @Column(name = "task_org_capacity")
    @Comment("jwt")
    private Integer taskOrgCapacity;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private OrganizationUnit parent;
    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    @JsonIgnore
    private Collection<OrganizationUnit> childOrganizationUnits;


    @JoinColumn(name = "cd_common_base_data_org_fundamental_base_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private Data commonBaseDataFundamentalBase;

    @JoinColumn(name = "unit_type", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private Data commonBaseDataUnitType;

    @JoinColumn(name = "cd_common_base_data_geography_code_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private Data commonBaseDataGeometricLocation;

    @JoinColumn(name = "province", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private Data commonBaseDataProvince;



    @JoinColumn(name = "city", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("jwt")
    private City city;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
    }

    public String getTellNumber() {
        return tellNumber;
    }

    public void setTellNumber(String tellNumber) {
        this.tellNumber = tellNumber;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getCodePath() {
        return codePath;
    }

    public void setCodePath(String codePath) {
        this.codePath = codePath;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getStartExpireDate() {
        return startExpireDate;
    }

    public void setStartExpireDate(Date startExpireDate) {
        this.startExpireDate = startExpireDate;
    }

    public Date getEndExpireDate() {
        return endExpireDate;
    }

    public void setEndExpireDate(Date endExpireDate) {
        this.endExpireDate = endExpireDate;
    }

    public Date getDateAuthorizationLetter() {
        return dateAuthorizationLetter;
    }

    public void setDateAuthorizationLetter(Date dateAuthorizationLetter) {
        this.dateAuthorizationLetter = dateAuthorizationLetter;
    }

    public String getAuthorizationLetterNumber() {
        return authorizationLetterNumber;
    }

    public void setAuthorizationLetterNumber(String authorizationLetterNumber) {
        this.authorizationLetterNumber = authorizationLetterNumber;
    }

    public Double getGeometricLocation_x() {
        return geometricLocation_x;
    }

    public void setGeometricLocation_x(Double geometricLocation_x) {
        this.geometricLocation_x = geometricLocation_x;
    }

    public Double getGeometricLocation_y() {
        return geometricLocation_y;
    }

    public void setGeometricLocation_y(Double geometricLocation_y) {
        this.geometricLocation_y = geometricLocation_y;
    }

    public Integer getWorkOrgCapacity() {
        return workOrgCapacity;
    }

    public void setWorkOrgCapacity(Integer workOrgCapacity) {
        this.workOrgCapacity = workOrgCapacity;
    }

    public Integer getTaskOrgCapacity() {
        return taskOrgCapacity;
    }

    public void setTaskOrgCapacity(Integer taskOrgCapacity) {
        this.taskOrgCapacity = taskOrgCapacity;
    }

    public OrganizationUnit getParent() {
        return parent;
    }

    public void setParent(OrganizationUnit parent) {
        this.parent = parent;
    }

    public Collection<OrganizationUnit> getChildOrganizationUnits() {
        return childOrganizationUnits;
    }

    public void setChildOrganizationUnits(Collection<OrganizationUnit> childOrganizationUnits) {
        this.childOrganizationUnits = childOrganizationUnits;
    }

    public Data getCommonBaseDataFundamentalBase() {
        return commonBaseDataFundamentalBase;
    }

    public void setCommonBaseDataFundamentalBase(Data commonBaseDataFundamentalBase) {
        this.commonBaseDataFundamentalBase = commonBaseDataFundamentalBase;
    }

    public Data getCommonBaseDataUnitType() {
        return commonBaseDataUnitType;
    }

    public void setCommonBaseDataUnitType(Data commonBaseDataUnitType) {
        this.commonBaseDataUnitType = commonBaseDataUnitType;
    }

    public Data getCommonBaseDataGeometricLocation() {
        return commonBaseDataGeometricLocation;
    }

    public void setCommonBaseDataGeometricLocation(Data commonBaseDataGeometricLocation) {
        this.commonBaseDataGeometricLocation = commonBaseDataGeometricLocation;
    }

    public Data getCommonBaseDataProvince() {
        return commonBaseDataProvince;
    }

    public void setCommonBaseDataProvince(Data commonBaseDataProvince) {
        this.commonBaseDataProvince = commonBaseDataProvince;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
