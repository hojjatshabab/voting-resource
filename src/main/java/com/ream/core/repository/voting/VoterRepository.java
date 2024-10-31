package com.ream.core.repository.voting;

import com.ream.core.domain.voting.Voter;
import com.ream.core.repository.voting.extra.MemberExtraRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VoterRepository extends JpaRepository<Voter, UUID>, MemberExtraRepository {

}
