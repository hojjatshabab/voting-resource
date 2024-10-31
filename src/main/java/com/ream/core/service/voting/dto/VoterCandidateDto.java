package com.ream.core.service.voting.dto;

import java.util.UUID;

public class VoterCandidateDto {

    private UUID id;
    private UUID candidateId;
    private UUID voterId;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(UUID candidateId) {
        this.candidateId = candidateId;
    }

    public UUID getVoterId() {
        return voterId;
    }

    public void setVoterId(UUID voterId) {
        this.voterId = voterId;
    }
}
