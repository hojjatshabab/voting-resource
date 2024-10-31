package com.ream.core.service.voting.dto;

import java.util.UUID;

public class CandidateDto {
    private UUID id;
    private UUID memberId;
    private String memberName;
    private UUID votingId;
    private String votingName;
    private String code;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UUID getMemberId() {
        return memberId;
    }

    public void setMemberId(UUID memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public UUID getVotingId() {
        return votingId;
    }

    public void setVotingId(UUID votingId) {
        this.votingId = votingId;
    }

    public String getVotingName() {
        return votingName;
    }

    public void setVotingName(String votingName) {
        this.votingName = votingName;
    }
}
