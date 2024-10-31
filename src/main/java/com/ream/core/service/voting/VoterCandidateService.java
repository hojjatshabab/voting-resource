package com.ream.core.service.voting;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.MemberDto;
import com.ream.core.service.voting.dto.VoterCandidateDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoterCandidateService {
    VoterCandidateDto save(VoterCandidateDto voterCandidateDto);

    VoterCandidateDto update(VoterCandidateDto voterCandidateDto);

    PageResponse<VoterCandidateDto> findAll(PageRequest<VoterCandidateDto> model);

    List<VoterCandidateDto> findAll();

    Optional<VoterCandidateDto> findById(UUID id);

    Boolean deleteById(UUID id);

}
