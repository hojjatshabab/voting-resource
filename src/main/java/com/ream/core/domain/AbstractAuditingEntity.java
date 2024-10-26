package com.ream.core.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.security.User;
import com.ream.core.service.security.UserService;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.ApplicationContext;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static jakarta.persistence.GenerationType.AUTO;

@Data
@Configurable
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractAuditingEntity<T extends Serializable> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = AUTO)
    private T id;
    @Column(name = "created_by", nullable = true, length = 50, updatable = false)
    @JsonIgnore
    private UUID createdBy;
    @Column(name = "modified_by", length = 50)
    @JsonIgnore
    private UUID modifiedBy;
    @CreatedDate
    @Column(name = "creation_date_time", updatable = false)
    @JsonIgnore
    private Instant creationDateTime = Instant.now();
    @LastModifiedDate
    @Column(name = "modification_date_time")
    @JsonIgnore
    private Instant modificationDateTime = Instant.now();
    @Column(name = "deleted")
    private Boolean deleted;
    @Column(name = "log_extra_info")
    private String logExtraInfo;
    @PrePersist
    @PreUpdate
    public void insertCreationAndModification() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails details = (UserDetails) authentication.getPrincipal();
//            String username = details.getUsername();
            UUID userId=((User) details).getId();
            if (createdBy == null) {

                createdBy = Objects.isNull(userId) ? UUID.fromString("48a73c81-41bc-4a63-8e57-c276ef790990"):userId;
            }
            modifiedBy = userId;
        }
        if(createdBy==null)  createdBy =  UUID.fromString("48a73c81-41bc-4a63-8e57-c276ef790990");
        this.deleted=false;
        this.logExtraInfo="";
    }
    public T getId() {
        return id;
    }
    public void setId(T id) {
        this.id = id;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }

    public UUID getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(UUID modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Instant getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(Instant creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public Instant getModificationDateTime() {
        return modificationDateTime;
    }

    public void setModificationDateTime(Instant modificationDateTime) {
        this.modificationDateTime = modificationDateTime;
    }
    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getLogExtraInfo() {
        return logExtraInfo;
    }

    public void setLogExtraInfo(String logExtraInfo) {
        this.logExtraInfo = logExtraInfo;
    }
}