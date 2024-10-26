package com.ream.core.domain.security;

import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_setting", schema = "public")
public class SecuritySetting extends AbstractAuditingEntity<UUID> {

    @Basic
    @Column(name = "failed_login_count", nullable = false)
    private Integer failedLoginCount;
    @Basic
    @Column(name = "expire_day", nullable = false)
    private Integer expireDay;


    public Integer getFailedLoginCount() {
        return failedLoginCount;
    }

    public void setFailedLoginCount(Integer failedLoginCount) {
        this.failedLoginCount = failedLoginCount;
    }

    public Integer getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(Integer expireDay) {
        this.expireDay = expireDay;
    }

}
