package com.ream.core.domain.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "voter_candidate", schema = "voting_info")
public class VoterCandidate extends AbstractAuditingEntity<UUID> {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate", referencedColumnName = "id")
    @JsonIgnore
    @Comment("نامزد")
    private Candidate candidate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voter", referencedColumnName = "id")
    @JsonIgnore
    @Comment("رای دهنده")
    private Voter voter;

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Voter getVoter() {
        return voter;
    }

    public void setVoter(Voter voter) {
        this.voter = voter;
    }
}
