package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.security.User;
import com.ream.core.model.Priority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_log_history", schema = "public")
public class LogHistory extends AbstractAuditingEntity<UUID> {

    @Basic
    @Column(name = "action_name", nullable = false)
    private String actionName;

    @Basic
    @Column(name = "action_date", nullable = false)
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    private LocalDate  actionDate;

    @Basic
    @Column(name = "action_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    private LocalTime actionTime;
    @Basic
    @Column(name = "description", nullable = true)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private Priority priority; // فیلد جدید برای اولویت


    @Basic
    @Column(name = "ip_address", nullable = true)
    private String ipAddress;  // فیلد جدید برای آدرس IP


    @Basic
    @Column(name = "username", nullable = true)
    private String userName;  // فیلد جدید برای userName


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void insertCreationAndModification() {
        if (actionDate == null) {
            actionDate = LocalDate.now(); // تاریخ فعلی
        }
        if (actionTime == null) {
            actionTime = LocalTime.now(); // زمان فعلی
        }
        if (priority == null) {
            priority = Priority.MEDIUM; // اولویت پیش‌فرض
        }
        super.insertCreationAndModification();
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
}
