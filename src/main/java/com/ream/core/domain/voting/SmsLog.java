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
@Table(name = "sms_log", schema = "voting_info")
public class SmsLog extends AbstractAuditingEntity<UUID> {

    @Column(name = "msg_id")
    @Comment("شناسه پیامک")
    private String msgId;

    @Column(name = "status")
    @Comment("وضعیت پیامک")
    private Integer status;

    @Column(name = "user_id")
    @Comment("کاربر")
    private UUID userId;

    @Column(name = "persian_status")
    @Comment("وضعیت فارسی")
    private String persianStatus;

    @Column(name = "send_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", locale = "IR", timezone = "Asia/Tehran")
    @Comment("زمان ارسال")
    @Temporal(TemporalType.DATE)
    private Date sendTime;

    @Column(name = "deliver_time")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("زمان تحویل")
    private Date deliverTime;

    @Column(name = "message")
    @Comment("پیام")
    private String message;

    @Column(name = "mobile_number")
    @Comment("شماره موبایل")
    private String mobileNumber;

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

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getPersianStatus() {
        return persianStatus;
    }

    public void setPersianStatus(String persianStatus) {
        this.persianStatus = persianStatus;
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
