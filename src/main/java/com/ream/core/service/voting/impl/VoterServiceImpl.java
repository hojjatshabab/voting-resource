package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.Voter;
import com.ream.core.repository.voting.VoterRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.VoterService;
import com.ream.core.service.voting.dto.VoterDto;
import com.ream.core.service.voting.mapper.VoterDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VoterServiceImpl implements VoterService {
    private final VoterRepository voterRepository;
    private final VoterDtoMapper voterDtoMapper;

    public VoterServiceImpl(VoterRepository voterRepository, VoterDtoMapper voterDtoMapper) {
        this.voterRepository = voterRepository;
        this.voterDtoMapper = voterDtoMapper;
    }

    @Override
    public VoterDto save(VoterDto voterDto) {
        return voterDtoMapper.toDto(voterRepository.save(voterDtoMapper.toEntity(voterDto)));
    }

    @Override
    public VoterDto update(VoterDto voterDto) {
        return voterDtoMapper.toDto(voterRepository.save(voterDtoMapper.toEntity(voterDto)));
    }

    @Override
    public PageResponse<VoterDto> findAll(PageRequest<VoterDto> model) {
        List<VoterDto> result = voterRepository
                .findAll(Pageable.ofSize(model.getPageSize())
                        .withPage(model.getCurrentPage() - 1))
                .stream().map(voterDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = voterRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<VoterDto> findById(UUID id) {
        Optional<Voter> optionalVoter = voterRepository.findById(id);
        if (optionalVoter.isPresent()) {
            return Optional.ofNullable(
                    voterDtoMapper.toDto(optionalVoter.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        voterRepository.deleteById(id);
        return true;
    }

}
