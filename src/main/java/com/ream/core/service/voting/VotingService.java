package com.ream.core.service.voting;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.MemberDto;
import com.ream.core.service.voting.dto.VotingDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VotingService {
    VotingDto save(VotingDto votingDto);

    VotingDto update(VotingDto votingDto);

    PageResponse<VotingDto> findAll(PageRequest<VotingDto> model);

    Optional<VotingDto> findById(UUID id);

    Boolean deleteById(UUID id);

}
