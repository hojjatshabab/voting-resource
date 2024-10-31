package com.ream.core.service.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String fatherName;
    private String nationalCode;
    private String code;
    private String phoneNumber;
    private String mobileNumber;
    private String email;
    private String address;
    private String image;
    private String insuranceNumber;
    private String insurancePerson;
    private String workshopNumber;
    private Date birthDate;
    private UUID performerId;
    private String performerName;
    private UUID birthPlaceId;
    private String birthPlaceName;
    private UUID residencePlaceId;
    private String residencePlaceName;
    private UUID employmentPlaceId;
    private String employmentPlaceName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(String insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public String getInsurancePerson() {
        return insurancePerson;
    }

    public void setInsurancePerson(String insurancePerson) {
        this.insurancePerson = insurancePerson;
    }

    public String getWorkshopNumber() {
        return workshopNumber;
    }

    public void setWorkshopNumber(String workshopNumber) {
        this.workshopNumber = workshopNumber;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public UUID getPerformerId() {
        return performerId;
    }

    public void setPerformerId(UUID performerId) {
        this.performerId = performerId;
    }

    public String getPerformerName() {
        return performerName;
    }

    public void setPerformerName(String performerName) {
        this.performerName = performerName;
    }

    public UUID getBirthPlaceId() {
        return birthPlaceId;
    }

    public void setBirthPlaceId(UUID birthPlaceId) {
        this.birthPlaceId = birthPlaceId;
    }

    public String getBirthPlaceName() {
        return birthPlaceName;
    }

    public void setBirthPlaceName(String birthPlaceName) {
        this.birthPlaceName = birthPlaceName;
    }

    public UUID getResidencePlaceId() {
        return residencePlaceId;
    }

    public void setResidencePlaceId(UUID residencePlaceId) {
        this.residencePlaceId = residencePlaceId;
    }

    public String getResidencePlaceName() {
        return residencePlaceName;
    }

    public void setResidencePlaceName(String residencePlaceName) {
        this.residencePlaceName = residencePlaceName;
    }

    public UUID getEmploymentPlaceId() {
        return employmentPlaceId;
    }

    public void setEmploymentPlaceId(UUID employmentPlaceId) {
        this.employmentPlaceId = employmentPlaceId;
    }

    public String getEmploymentPlaceName() {
        return employmentPlaceName;
    }

    public void setEmploymentPlaceName(String employmentPlaceName) {
        this.employmentPlaceName = employmentPlaceName;
    }
}
