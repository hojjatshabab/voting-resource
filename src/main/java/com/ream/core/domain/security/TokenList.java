package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.model.Priority;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_token_list", schema = "public")
public class TokenList extends AbstractAuditingEntity<UUID> {


    @Column(name = "action_name",nullable = false, length=10485760)
    private String token;


    @Column(name = "username", nullable = true)
    private String username;


    @Column(name = "ip_address", nullable = true)
    private String ipAddress;


    @Basic
    @Column(name = "action_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    private LocalDate actionDate;

    @Basic
    @Column(name = "action_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    private LocalTime actionTime;


    @Override
    public void insertCreationAndModification() {
        if (actionDate == null) {
            actionDate = LocalDate.now(); // تاریخ فعلی
        }
        if (actionTime == null) {
            actionTime = LocalTime.now(); // زمان فعلی
        }
        super.insertCreationAndModification();
    }

    public LocalDate getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDate actionDate) {
        this.actionDate = actionDate;
    }

    public LocalTime getActionTime() {
        return actionTime;
    }

    public void setActionTime(LocalTime actionTime) {
        this.actionTime = actionTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}
