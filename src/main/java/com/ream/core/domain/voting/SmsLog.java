package com.ream.core.domain.voting;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
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
@Table(name = "ap_sms_log", schema = "voting-info")
public class SmsLog extends AbstractAuditingEntity<UUID> {
    @Column(name = "msg_id", nullable = true)
    @Comment("jwt")
    private String msgId;
    @Column(name = "status")
    @Comment("jwt")
    private Integer status;
    @Column(name = "user_id", nullable = true)
    @Comment("jwt")
    private UUID userBy;

    @Column(name = "sms_status")
    @Comment("jwt")
    private String persianStep;
    @Column(name = "sms_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    @Temporal(TemporalType.DATE)
    private Date sendTime;
    @Column(name = "deliver_time")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("jwt")
    private Date deliverTime;

    @Column(name = "message")
    @Comment("jwt")
    private String message;

    @Column(name = "mobile_number", nullable = true)
    @Comment("jwt")
    private String destination;


    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UUID getUserBy() {
        return userBy;
    }

    public void setUserBy(UUID userBy) {
        this.userBy = userBy;
    }

    public String getPersianStep() {
        return persianStep;
    }

    public void setPersianStep(String persianStep) {
        this.persianStep = persianStep;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getDeliverTime() {
        return deliverTime;
    }

    public void setDeliverTime(Date deliverTime) {
        this.deliverTime = deliverTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
