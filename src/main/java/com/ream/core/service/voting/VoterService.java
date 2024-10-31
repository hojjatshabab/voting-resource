package com.ream.core.service.voting;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.MemberDto;
import com.ream.core.service.voting.dto.VoterDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VoterService {
    VoterDto save(VoterDto voterDto);

    VoterDto update(VoterDto voterDto);

    PageResponse<VoterDto> findAll(PageRequest<VoterDto> model);

    Optional<VoterDto> findById(UUID id);

    Boolean deleteById(UUID id);

}
