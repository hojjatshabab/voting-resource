package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.Candidate;
import com.ream.core.domain.voting.Member;
import com.ream.core.repository.voting.CandidateRepository;
import com.ream.core.repository.voting.MemberRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.CandidateService;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.dto.CandidateDto;
import com.ream.core.service.voting.dto.MemberDto;
import com.ream.core.service.voting.mapper.CandidateDtoMapper;
import com.ream.core.service.voting.mapper.MemberDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class CandidateServiceImpl implements CandidateService {
    private final CandidateRepository candidateRepository;
    private final CandidateDtoMapper candidateDtoMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, CandidateDtoMapper candidateDtoMapper) {
        this.candidateRepository = candidateRepository;
        this.candidateDtoMapper = candidateDtoMapper;
    }

    @Override
    public CandidateDto save(CandidateDto candidateDto) {
        return candidateDtoMapper.toDto(candidateRepository.save(candidateDtoMapper.toEntity(candidateDto)));
    }

    @Override
    public CandidateDto update(CandidateDto candidateDto) {
        return candidateDtoMapper.toDto(candidateRepository.save(candidateDtoMapper.toEntity(candidateDto)));
    }

    @Override
    public PageResponse<CandidateDto> findAll(PageRequest<CandidateDto> model) {
        List<CandidateDto> result = candidateRepository
                .findAll(Pageable.ofSize(model.getPageSize())
                        .withPage(model.getCurrentPage() - 1))
                .stream().map(candidateDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = candidateRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<CandidateDto> findById(UUID id) {
        Optional<Candidate> optionalCandidate = candidateRepository.findById(id);
        if (optionalCandidate.isPresent()) {
            return Optional.ofNullable(
                    candidateDtoMapper.toDto(optionalCandidate.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        candidateRepository.deleteById(id);
        return true;
    }

}
