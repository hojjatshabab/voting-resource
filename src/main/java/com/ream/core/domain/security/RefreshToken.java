package com.ream.core.domain.security;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.Date;
import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_refresh_token", schema = "public")
public class RefreshToken extends AbstractAuditingEntity<UUID> {
    @Column(name = "token", nullable = false,length = 10485760)
    private String token;
    @Column(name = "user_id", nullable = false)
    @Comment("expiryDate")
    private UUID userId;



    @Column(name = "expiry_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("expiryDate")
    private Date expiryDate;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
}
