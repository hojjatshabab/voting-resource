package com.ream.core.domain.baseInfo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "performer", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"code_path"}),
        @UniqueConstraint(columnNames = {"code"})
})
public class OrganizationUnit extends AbstractAuditingEntity<UUID> {
    @Column(name = "name", nullable = false)
    @Comment("نام")
    private String name;
    @Column(name = "address")
    @Comment("آدرس")
    private String address;
    @Column(name = "server_ip")
    @Comment("آدرس سرور")
    private String serverAddress;
    @Column(name = "email")
    @Email
    @Comment("ایمیل")
    private String email;
    @Column(name = "complete_name")
    @Comment("نام کامل")
    private String completeName;
    @Column(name = "tell_number")
    @Comment("شماره تلفن")
    private String tellNumber;
    @Column(name = "code", nullable = false)
    @Comment("کد")
    private String code;
    @Column(name = "active", nullable = false)
    @Comment("فعال/ غیر فال")
    private Boolean active;
    @Column(name = "code_path", nullable = false)
    @Comment("کد مسیر")
    private String codePath;
    @Column(name = "priority")
    @Comment("اولویت")
    private Integer priority;
    @Column(name = "start_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("تاریخ شروع")
    private Date startExpireDate;
    @Column(name = "expire_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("تاریخ انقضا")
    private Date endExpireDate;
    @Column(name = "justification_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("تاریخ نامه")
    private Date dateAuthorizationLetter;
    @Column(name = "authorization_letter_number")
    @Comment("شماره نماه")
    private String authorizationLetterNumber;
    @Column(name = "geometric_location_x")
    @Comment("مختصات طولی")
    private Double geometricLocation_x;
    @Column(name = "geometric_location_y")
    @Comment("مختصات عرضی")
    private Double geometricLocation_y;
    @Column(name = "work_org_capacity")
    @Comment("ظرفیت کادر")
    private Integer workOrgCapacity;
    @Column(name = "task_org_capacity")
    @Comment("ظرفیت وظیفه")
    private Integer taskOrgCapacity;

    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("پدر")
    private OrganizationUnit parent;

    @JoinColumn(name = "common_base_data_org_fundamental_base_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("فاندامنتال")
    private CommonBaseData commonBaseDataFundamentalBase;

    @JoinColumn(name = "common_base_dataunit_type", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("نوع سازمان")
    private CommonBaseData commonBaseDataUnitType;

    @JoinColumn(name = "common_base_data_geography_code_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("مختصات")
    private CommonBaseData commonBaseDataGeometricLocation;

    @JoinColumn(name = "common_base_data_province", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("استان")
    private CommonBaseData commonBaseDataProvince;

    @JoinColumn(name = "city", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Comment("شهر")
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

    public CommonBaseData getCommonBaseDataFundamentalBase() {
        return commonBaseDataFundamentalBase;
    }

    public void setCommonBaseDataFundamentalBase(CommonBaseData commonBaseDataFundamentalBase) {
        this.commonBaseDataFundamentalBase = commonBaseDataFundamentalBase;
    }

    public CommonBaseData getCommonBaseDataUnitType() {
        return commonBaseDataUnitType;
    }

    public void setCommonBaseDataUnitType(CommonBaseData commonBaseDataUnitType) {
        this.commonBaseDataUnitType = commonBaseDataUnitType;
    }

    public CommonBaseData getCommonBaseDataGeometricLocation() {
        return commonBaseDataGeometricLocation;
    }

    public void setCommonBaseDataGeometricLocation(CommonBaseData commonBaseDataGeometricLocation) {
        this.commonBaseDataGeometricLocation = commonBaseDataGeometricLocation;
    }

    public CommonBaseData getCommonBaseDataProvince() {
        return commonBaseDataProvince;
    }

    public void setCommonBaseDataProvince(CommonBaseData commonBaseDataProvince) {
        this.commonBaseDataProvince = commonBaseDataProvince;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
