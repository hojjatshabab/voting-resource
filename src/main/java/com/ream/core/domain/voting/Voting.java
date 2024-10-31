package com.ream.core.domain.voting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ream.core.domain.AbstractAuditingEntity;
import com.ream.core.domain.baseInfo.OrganizationUnit;
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
@Table(name = "voting", schema = "voting_info")
public class Voting extends AbstractAuditingEntity<UUID> {

    @Column(name = "subject")
    @Comment("موضوع")
    private String subject;

    @Column(name = "description")
    @Comment("شرح")
    private String description;

    @Column(name = "status")
    @Comment("وضعیت")
    private String status;

    @Column(name = "voting_date")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "IR", timezone = "Asia/Tehran")
    @Comment("تاریخ رای گیری")
    private Date votingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performer", referencedColumnName = "id")
    @JsonIgnore
    @Comment("سازمان")
    private OrganizationUnit performer;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getVotingDate() {
        return votingDate;
    }

    public void setVotingDate(Date votingDate) {
        this.votingDate = votingDate;
    }

    public OrganizationUnit getPerformer() {
        return performer;
    }

    public void setPerformer(OrganizationUnit performer) {
        this.performer = performer;
    }
}
