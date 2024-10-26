package com.ream.core.domain.security;

import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@lombok.Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "jw_black_list_token", schema = "public")
public class BlackListToken extends AbstractAuditingEntity<UUID> {

    @Column(name = "action_name", nullable = false,length = 10485760)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
