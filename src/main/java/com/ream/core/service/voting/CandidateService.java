package com.ream.core.service.voting;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.CandidateDto;
import com.ream.core.service.voting.dto.MemberDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CandidateService {
    CandidateDto save(CandidateDto candidateDto);

    CandidateDto update(CandidateDto candidateDto);

    PageResponse<CandidateDto> findAll(PageRequest<CandidateDto> model);

    Optional<CandidateDto> findById(UUID id);

    Boolean deleteById(UUID id);

}
