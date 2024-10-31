package com.ream.core.domain.voting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.baseInfo.City;
import com.ream.core.domain.baseInfo.OrganizationUnit;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member", schema = "voting_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"national_code"})
})
public class Member extends AbstractAuditingEntity<UUID> {

    @Column(name = "first_name")
    @Comment("نام")
    private String firstName;

    @Column(name = "last_name")
    @Comment("نام خانوادگی")
    private String lastName;

    @Column(name = "father_name")
    @Comment("نام پدر")
    private String fatherName;

    @Column(name = "national_code")
    @Comment("شماره ملی")
    private String nationalCode;

    @Column(name = "code")
    @Comment("کد")
    private String code;

    @Column(name = "phone_number")
    @Comment("شماره موبایل")
    private String phoneNumber;

    @Column(name = "mobile_number")
    @Comment("شماره تلفن")
    private String mobileNumber;

    @Column(name = "email")
    @Email
    @Comment("آدرس ایمیل")
    private String email;

    @Column(name = "address")
    @Comment("آدرس")
    private String address;

    @Column(name = "image")
    @Comment("عکس")
    private String image;

    @Column(name = "insurance_number")
    @Comment("شماره بیمه")
    private String insuranceNumber;

    @Column(name = "insurance_person")
    @Comment("مستمری")
    private String insurancePerson;

    @Column(name = "workshop_number")
    @Comment("شماره")
    private String workshopNumber;

    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("تاریخ تولد")
    private Date birthDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer", referencedColumnName = "id")
    @JsonIgnore
    @Comment("سازمان")
    private OrganizationUnit performer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_place", referencedColumnName = "id")
    @JsonIgnore
    @Comment("محل تولد")
    private City birthPlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "residence_place", referencedColumnName = "id")
    @JsonIgnore
    @Comment("محل سکونت")
    private City residencePlace;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_place", referencedColumnName = "id")
    @JsonIgnore
    @Comment("محل کار")
    private City employmentPlace;

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

    public OrganizationUnit getPerformer() {
        return performer;
    }

    public void setPerformer(OrganizationUnit performer) {
        this.performer = performer;
    }

    public City getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(City birthPlace) {
        this.birthPlace = birthPlace;
    }

    public City getResidencePlace() {
        return residencePlace;
    }

    public void setResidencePlace(City residencePlace) {
        this.residencePlace = residencePlace;
    }

    public City getEmploymentPlace() {
        return employmentPlace;
    }

    public void setEmploymentPlace(City employmentPlace) {
        this.employmentPlace = employmentPlace;
    }
}
