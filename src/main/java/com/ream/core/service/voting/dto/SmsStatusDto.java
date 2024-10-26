package com.ream.core.service.voting.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SmsStatusDto {


    private String MsgID;
    private String Status;
    private String persianStep;
    private String SendTime;
    private String DeliverTime;


    public String getMsgID() {
        return MsgID;
    }

    public void setMsgID(String msgID) {
        MsgID = msgID;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getPersianStep() {
        return persianStep;
    }

    public void setPersianStep(String persianStep) {
        this.persianStep = persianStep;
    }

    public String getSendTime() {
        return SendTime;
    }

    public void setSendTime(String sendTime) {
        SendTime = sendTime;
    }

    public String getDeliverTime() {
        return DeliverTime;
    }

    public void setDeliverTime(String deliverTime) {
        DeliverTime = deliverTime;
    }
}
