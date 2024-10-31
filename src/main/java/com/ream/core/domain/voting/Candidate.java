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
@Table(name = "candidate", schema = "voting_info")
public class Candidate extends AbstractAuditingEntity<UUID> {

    @Column(name = "code")
    @Comment("کد")
    private String code;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

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
