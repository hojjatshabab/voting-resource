package com.ream.core.service.baseInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUnitDto {

    private UUID id;
    private String name;
    private String address;
    private String serverAddress;
    private String email;
    private String tellNumber;
    private String code;
    private Boolean active;
    private String codePath;
    private Integer priority;
    private String completeName;
    private Date startExpireDate;
    private Date endExpireDate;
    private Date dateAuthorizationLetter;
    private String authorizationLetterNumber;
    private Long commonBaseDataFundamentalBaseId;
    private String commonBaseDataFundamentalBaseName;
    private Long commonBaseDataUnitTypeId;
    private String commonBaseDataUnitTypeName;
    private Integer workOrgCapacity;
    private Integer taskOrgCapacity;
    private Double geometricLocation_x;
    private Double geometricLocation_y;
    private Long commonBaseDataProvinceId;
    private String commonBaseDataProvinceName;
    private Long commonBaseDataGeometricLocationId;
    private String commonBaseDataGeometricLocationName;
    private UUID parentId;
    private String parentName;
    private UUID cityId;
    private String cityName;
    private List<OrganizationUnitDto> children;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public String getCompleteName() {
        return completeName;
    }

    public void setCompleteName(String completeName) {
        this.completeName = completeName;
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

    public Long getCommonBaseDataFundamentalBaseId() {
        return commonBaseDataFundamentalBaseId;
    }

    public void setCommonBaseDataFundamentalBaseId(Long commonBaseDataFundamentalBaseId) {
        this.commonBaseDataFundamentalBaseId = commonBaseDataFundamentalBaseId;
    }

    public String getCommonBaseDataFundamentalBaseName() {
        return commonBaseDataFundamentalBaseName;
    }

    public void setCommonBaseDataFundamentalBaseName(String commonBaseDataFundamentalBaseName) {
        this.commonBaseDataFundamentalBaseName = commonBaseDataFundamentalBaseName;
    }

    public Long getCommonBaseDataUnitTypeId() {
        return commonBaseDataUnitTypeId;
    }

    public void setCommonBaseDataUnitTypeId(Long commonBaseDataUnitTypeId) {
        this.commonBaseDataUnitTypeId = commonBaseDataUnitTypeId;
    }

    public String getCommonBaseDataUnitTypeName() {
        return commonBaseDataUnitTypeName;
    }

    public void setCommonBaseDataUnitTypeName(String commonBaseDataUnitTypeName) {
        this.commonBaseDataUnitTypeName = commonBaseDataUnitTypeName;
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

    public Long getCommonBaseDataProvinceId() {
        return commonBaseDataProvinceId;
    }

    public void setCommonBaseDataProvinceId(Long commonBaseDataProvinceId) {
        this.commonBaseDataProvinceId = commonBaseDataProvinceId;
    }

    public String getCommonBaseDataProvinceName() {
        return commonBaseDataProvinceName;
    }

    public void setCommonBaseDataProvinceName(String commonBaseDataProvinceName) {
        this.commonBaseDataProvinceName = commonBaseDataProvinceName;
    }

    public Long getCommonBaseDataGeometricLocationId() {
        return commonBaseDataGeometricLocationId;
    }

    public void setCommonBaseDataGeometricLocationId(Long commonBaseDataGeometricLocationId) {
        this.commonBaseDataGeometricLocationId = commonBaseDataGeometricLocationId;
    }

    public String getCommonBaseDataGeometricLocationName() {
        return commonBaseDataGeometricLocationName;
    }

    public void setCommonBaseDataGeometricLocationName(String commonBaseDataGeometricLocationName) {
        this.commonBaseDataGeometricLocationName = commonBaseDataGeometricLocationName;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public UUID getCityId() {
        return cityId;
    }

    public void setCityId(UUID cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<OrganizationUnitDto> getChildren() {
        return children;
    }

    public void setChildren(List<OrganizationUnitDto> children) {
        this.children = children;
    }
}
