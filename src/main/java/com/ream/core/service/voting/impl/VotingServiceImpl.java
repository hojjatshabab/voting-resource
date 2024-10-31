package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.Voting;
import com.ream.core.repository.voting.VotingRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.VotingService;
import com.ream.core.service.voting.dto.VotingDto;
import com.ream.core.service.voting.mapper.VotingDtoMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class VotingServiceImpl implements VotingService {
    private final VotingRepository votingRepository;
    private final VotingDtoMapper votingDtoMapper;

    public VotingServiceImpl(VotingRepository votingRepository, VotingDtoMapper votingDtoMapper) {
        this.votingRepository = votingRepository;
        this.votingDtoMapper = votingDtoMapper;
    }

    @Override
    public VotingDto save(VotingDto votingDto) {
        return votingDtoMapper.toDto(votingRepository.save(votingDtoMapper.toEntity(votingDto)));
    }

    @Override
    public VotingDto update(VotingDto votingDto) {
        return votingDtoMapper.toDto(votingRepository.save(votingDtoMapper.toEntity(votingDto)));
    }

    @Override
    public PageResponse<VotingDto> findAll(PageRequest<VotingDto> model) {
        List<VotingDto> result = votingRepository
                .findAll(Pageable.ofSize(model.getPageSize())
                        .withPage(model.getCurrentPage() - 1))
                .stream().map(votingDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = votingRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Optional<VotingDto> findById(UUID id) {
        Optional<Voting> optionalVoting = votingRepository.findById(id);
        if (optionalVoting.isPresent()) {
            return Optional.ofNullable(
                    votingDtoMapper.toDto(optionalVoting.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        votingRepository.deleteById(id);
        return true;
    }

}
