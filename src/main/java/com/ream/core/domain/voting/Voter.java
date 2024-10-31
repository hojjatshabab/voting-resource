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
@Table(name = "voter", schema = "voting_info")
public class Voter extends AbstractAuditingEntity<UUID> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member", referencedColumnName = "id")
    @JsonIgnore
    @Comment("اعضا")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting", referencedColumnName = "id")
    @JsonIgnore
    @Comment("فرآیند رای گیری")
    private Voting voting;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Voting getVoting() {
        return voting;
    }

    public void setVoting(Voting voting) {
        this.voting = voting;
    }
}
