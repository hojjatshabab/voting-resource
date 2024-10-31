package com.ream.core.service.voting.impl;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.VoterCandidateService;
import com.ream.core.service.voting.dto.VoterCandidateDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VoterCandidateServiceImpl implements VoterCandidateService {


    @Override
    public VoterCandidateDto save(VoterCandidateDto voterCandidateDto) {
        return null;
    }

    @Override
    public VoterCandidateDto update(VoterCandidateDto voterCandidateDto) {
        return null;
    }

    @Override
    public PageResponse<VoterCandidateDto> findAll(PageRequest<VoterCandidateDto> model) {
        return null;
    }

    @Override
    public List<VoterCandidateDto> findAll() {
        return null;
    }

    @Override
    public Optional<VoterCandidateDto> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        return null;
    }
}
