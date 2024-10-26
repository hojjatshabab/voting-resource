package com.ream.core.service.voting.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private UUID id;
    private Boolean finalityChoice;
    private Boolean finalityOrder;
    private String previousUnitPrice;

    private Double allocatedDifferencePriceMember;

    public Double getAllocatedDifferencePriceMember() {
        return allocatedDifferencePriceMember;
    }

    public void setAllocatedDifferencePriceMember(Double allocatedDifferencePriceMember) {
        this.allocatedDifferencePriceMember = allocatedDifferencePriceMember;
    }

    private String blockRow;

    public String getBlockRow() {
        return blockRow;
    }

    public void setBlockRow(String blockRow) {
        this.blockRow = blockRow;
    }

    private Double allocatedUnitValue;


    private Double allocatedDifferencePrice;


    public Double getAllocatedUnitValue() {
        return allocatedUnitValue;
    }

    public void setAllocatedUnitValue(Double allocatedUnitValue) {
        this.allocatedUnitValue = allocatedUnitValue;
    }

    public Double getAllocatedDifferencePrice() {
        return allocatedDifferencePrice;
    }

    public void setAllocatedDifferencePrice(Double allocatedDifferencePrice) {
        this.allocatedDifferencePrice = allocatedDifferencePrice;
    }

    private String idCode;

    private UUID allocatedUnitId;

    private String allocatedUnitNumber;

    private Long allocatedUnitRank;

    private String systemStep;

    public String getSystemStep() {
        return systemStep;
    }

    public void setSystemStep(String systemStep) {
        this.systemStep = systemStep;
    }

    public String getAllocatedUnitNumber() {
        return allocatedUnitNumber;
    }

    public void setAllocatedUnitNumber(String allocatedUnitNumber) {
        this.allocatedUnitNumber = allocatedUnitNumber;
    }

    public Long getAllocatedUnitRank() {
        return allocatedUnitRank;
    }

    public void setAllocatedUnitRank(Long allocatedUnitRank) {
        this.allocatedUnitRank = allocatedUnitRank;
    }

    public UUID getAllocatedUnitId() {
        return allocatedUnitId;
    }

    public void setAllocatedUnitId(UUID allocatedUnitId) {
        this.allocatedUnitId = allocatedUnitId;
    }

    public String getUnitExpertValueH() {
        return UnitExpertValueH;
    }

    public void setUnitExpertValueH(String unitExpertValueH) {
        UnitExpertValueH = unitExpertValueH;
    }

    private String UnitExpertValueH;
    private String UnitExpertValue;
    private Boolean registered;
    private Boolean permissionSelectUnit;


    private Boolean unitSelectionRemoved;


    public Boolean getUnitSelectionRemoved() {
        return unitSelectionRemoved;
    }

    public void setUnitSelectionRemoved(Boolean unitSelectionRemoved) {
        this.unitSelectionRemoved = unitSelectionRemoved;
    }

    private Boolean permissionUpdate;

    private Boolean masterBackUp;

    public Boolean getMasterBackUp() {
        return masterBackUp;
    }

    public void setMasterBackUp(Boolean masterBackUp) {
        this.masterBackUp = masterBackUp;
    }


    public Boolean getPermissionUpdate() {
        return permissionUpdate;
    }

    public void setPermissionUpdate(Boolean permissionUpdate) {
        this.permissionUpdate = permissionUpdate;
    }

    public Boolean getPermissionSelectUnit() {
        return permissionSelectUnit;
    }

    public void setPermissionSelectUnit(Boolean permissionSelectUnit) {
        this.permissionSelectUnit = permissionSelectUnit;
    }





    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public String getUnitExpertValue() {
        return UnitExpertValue;
    }

    public void setUnitExpertValue(String unitExpertValue) {
        UnitExpertValue = unitExpertValue;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }


    public String getPreviousUnitPrice() {
        return previousUnitPrice;
    }

    public void setPreviousUnitPrice(String previousUnitPrice) {
        this.previousUnitPrice = previousUnitPrice;
    }

    private Date confirmChoiceDate;

    public Date getConfirmChoiceDate() {
        return confirmChoiceDate;
    }

    public void setConfirmChoiceDate(Date confirmChoiceDate) {
        this.confirmChoiceDate = confirmChoiceDate;
    }


    public Boolean getFinalityOrder() {
        return finalityOrder;
    }

    public void setFinalityOrder(Boolean finalityOrder) {
        this.finalityOrder = finalityOrder;
    }


    private Boolean finalityConfirm;

    public Boolean getFinalityConfirm() {
        return finalityConfirm;
    }

    public void setFinalityConfirm(Boolean finalityConfirm) {
        this.finalityConfirm = finalityConfirm;
    }

    private Boolean confirmSelection;

    public Boolean getConfirmSelection() {
        return confirmSelection;
    }

    public void setConfirmSelection(Boolean confirmSelection) {
        this.confirmSelection = confirmSelection;
    }


    private String confirmCode;


    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }


    public Boolean getFinalityChoice() {
        return finalityChoice;
    }

    public void setFinalityChoice(Boolean finalityChoice) {
        this.finalityChoice = finalityChoice;
    }

    private String accountNumber;


    private String address;


    private Date registerationDate;


    private String fatherName;


    private String firstName;

    private String lastName;


    private Boolean master;


    private String nationalCode;


    private String contractNumber;


    private String phoneNumber;


    private Date aggregationDate;


    private String mobileNumber;


    private Boolean selectionBySystem;


    private Integer selectionOrder;


    private Long commonBaseDataProvinceId;
    private String commonBaseDataProvinceName;

    private UUID cityId;
    private String cityName;


    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getRegisterationDate() {
        return registerationDate;
    }

    public void setRegisterationDate(Date registerationDate) {
        this.registerationDate = registerationDate;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getMaster() {
        return master;
    }

    public void setMaster(Boolean master) {
        this.master = master;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getAggregationDate() {
        return aggregationDate;
    }

    public void setAggregationDate(Date aggregationDate) {
        this.aggregationDate = aggregationDate;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public Boolean getSelectionBySystem() {
        return selectionBySystem;
    }

    public void setSelectionBySystem(Boolean selectionBySystem) {
        this.selectionBySystem = selectionBySystem;
    }

    public Integer getSelectionOrder() {
        return selectionOrder;
    }

    public void setSelectionOrder(Integer selectionOrder) {
        this.selectionOrder = selectionOrder;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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
}
