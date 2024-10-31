package com.ream.core.repository.voting;

import com.ream.core.domain.voting.VoterCandidate;
import com.ream.core.repository.voting.extra.MemberExtraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoterCandidateRepository extends JpaRepository<VoterCandidate, UUID>, MemberExtraRepository {

}
