package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.VoterCandidate;
import com.ream.core.repository.voting.VoterCandidateRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.VoterCandidateService;
import com.ream.core.service.voting.dto.VoterCandidateDto;
import com.ream.core.service.voting.mapper.VectorCandidateDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoterCandidateServiceImpl implements VoterCandidateService {
    private final VoterCandidateRepository voterCandidateRepository;
    private final VectorCandidateDtoMapper vectorCandidateDtoMapper;

    public VoterCandidateServiceImpl(VoterCandidateRepository voterCandidateRepository, VectorCandidateDtoMapper vectorCandidateDtoMapper) {
        this.voterCandidateRepository = voterCandidateRepository;
        this.vectorCandidateDtoMapper = vectorCandidateDtoMapper;
    }

    @Override
    public VoterCandidateDto save(VoterCandidateDto voterCandidateDto) {
        return vectorCandidateDtoMapper.toDto(voterCandidateRepository.save(vectorCandidateDtoMapper.toEntity(voterCandidateDto)));
    }

    @Override
    public VoterCandidateDto update(VoterCandidateDto voterCandidateDto) {
        return vectorCandidateDtoMapper.toDto(voterCandidateRepository.save(vectorCandidateDtoMapper.toEntity(voterCandidateDto)));
    }

    @Override
    public PageResponse<VoterCandidateDto> findAll(PageRequest<VoterCandidateDto> model) {
        List<VoterCandidateDto> result = voterCandidateRepository
                .findAll(Pageable.ofSize(model.getPageSize())
                        .withPage(model.getCurrentPage() - 1))
                .stream().map(vectorCandidateDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = voterCandidateRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<VoterCandidateDto> findById(UUID id) {
        Optional<VoterCandidate> optionalVoter = voterCandidateRepository.findById(id);
        if (optionalVoter.isPresent()) {
            return Optional.ofNullable(
                    vectorCandidateDtoMapper.toDto(optionalVoter.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        voterCandidateRepository.deleteById(id);
        return true;
    }
}
