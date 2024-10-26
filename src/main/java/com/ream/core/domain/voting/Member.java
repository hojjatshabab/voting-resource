package com.ream.core.domain.voting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.baseInfo.City;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Collection;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", schema = "voting-info"
)
public class Member extends AbstractAuditingEntity<UUID> {


    @Column(name = "account_number", nullable = true, unique = false)
    @Comment("jwt")
    private String accountNumber;


    @Column(name = "finality_choice", nullable = true, unique = false)
    @Comment("jwt")
    private Boolean finalityChoice;


    public Boolean getFinalityChoice() {
        return finalityChoice;
    }

    public void setFinalityChoice(Boolean finalityChoice) {
        this.finalityChoice = finalityChoice;
    }


    @Column(name = "address", nullable = true, unique = false)
    @Comment("jwt")
    private String address;


    @Column(name = "registeration_date", nullable = true, unique = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date registerationDate;


    @Basic(optional = false)
    @Column(name = "father_name", nullable = false, unique = false)
    @Comment("jwt")
    private String fatherName;


    @Basic(optional = false)

    @Column(name = "first_name", nullable = false, unique = false)
    @Comment("jwt")
    private String firstName;


    @Basic(optional = false)

    @Column(name = "last_name", nullable = false, unique = false)
    @Comment("jwt")
    private String lastName;


    @Basic(optional = false)
    @NotNull
    @Column(name = "is_master", nullable = false, unique = false)
    @Comment("jwt")
    private Boolean master;

    @Column(name = "permission_select_unit")
    @Comment("مجوز انتخاب واحد")
    private Boolean permissionSelectUnit;


    @Basic(optional = false)
    @Column(name = "national_code", nullable = false, unique = false)
    @Comment("jwt")
    private String nationalCode;


    @Column(name = "contract_number", nullable = true, unique = false)
    @Comment("jwt")
    private String contractNumber;


    @Basic(optional = false)


    @Column(name = "phone_number", nullable = false, unique = false)
    @Comment("jwt")
    private String phoneNumber;


    @Column(name = "aggregation_date", nullable = true, unique = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date aggregationDate;


    @Column(name = "mobile_number", nullable = true, unique = false)
    @Comment("jwt")
    private String mobileNumber;


    @Column(name = "selection_by_system")
    @Comment("jwt")
    private Boolean selectionBySystem;


    @Column(name = "previous_unit_price")
    @Comment("jwt")
    private String previousUnitPrice;


    public String getPreviousUnitPrice() {
        return previousUnitPrice;
    }

    public void setPreviousUnitPrice(String previousUnitPrice) {
        this.previousUnitPrice = previousUnitPrice;
    }

    @Column(name = "id_code")
    @Comment("jwt")
    private String idCode;

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }


    @Column(name = "allocated_unit_value")
    @Comment("ارزش واحد جدید")
    private Double allocatedUnitValue;


    @Column(name = "allocated_difference_price")
    @Comment("ماوتفاوت ارزش واحد ها")
    private Double allocatedDifferencePrice;

    @Column(name = "allocated_difference_price_member")
    @Comment("ماوتفاوت هر عضو")
    private Double allocatedDifferencePriceMember;


    public Double getAllocatedDifferencePriceMember() {
        return allocatedDifferencePriceMember;
    }

    public void setAllocatedDifferencePriceMember(Double allocatedDifferencePriceMember) {
        this.allocatedDifferencePriceMember = allocatedDifferencePriceMember;
    }

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



    @Column(name = "selection_order")
    @Comment("jwt")
    private Integer selectionOrder;

    @Column(name = "confirm_selection")
    @Comment("jwt")
    private Boolean confirmSelection;

    public Boolean getConfirmSelection() {
        return confirmSelection;
    }

    public void setConfirmSelection(Boolean confirmSelection) {
        this.confirmSelection = confirmSelection;
    }

    @Column(name = "confirm_code")
    @Comment("jwt")
    private String confirmCode;


    public String getConfirmCode() {
        return confirmCode;
    }

    public void setConfirmCode(String confirmCode) {
        this.confirmCode = confirmCode;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cd_common_base_data_bank_code", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    @Comment("jwt")
    private com.ream.core.domain.baseInfo.Data commonBaseDataProvince;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city", referencedColumnName = "id", nullable = true)
    @JsonIgnore
    @Comment("jwt")
    private City city;


    @Column(name = "unit_selection_removed")
    @Comment("jwt")
    private Boolean unitSelectionRemoved;

    public Boolean getUnitSelectionRemoved() {
        return unitSelectionRemoved;
    }

    public void setUnitSelectionRemoved(Boolean unitSelectionRemoved) {
        this.unitSelectionRemoved = unitSelectionRemoved;
    }

    @Column(name = "is_master_back_up")
    @Comment("jwt")
    private Boolean masterBackUp;

    public Boolean getMasterBackUp() {
        return masterBackUp;
    }

    public void setMasterBackUp(Boolean masterBackUp) {
        this.masterBackUp = masterBackUp;
    }

    @Column(name = "confirm_choice_date", nullable = true, unique = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date confirmChoiceDate;

    public Boolean getPermissionSelectUnit() {
        return permissionSelectUnit;
    }

    public void setPermissionSelectUnit(Boolean permissionSelectUnit) {
        this.permissionSelectUnit = permissionSelectUnit;
    }

    public Date getConfirmChoiceDate() {
        return confirmChoiceDate;
    }

    public void setConfirmChoiceDate(Date confirmChoiceDate) {
        this.confirmChoiceDate = confirmChoiceDate;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

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

    public com.ream.core.domain.baseInfo.Data getCommonBaseDataProvince() {
        return commonBaseDataProvince;
    }

    public void setCommonBaseDataProvince(com.ream.core.domain.baseInfo.Data commonBaseDataProvince) {
        this.commonBaseDataProvince = commonBaseDataProvince;
    }


}
