/*
package com.ream.core.service.voting.impl;

import com.ream.core.domain.voting.Member;
import com.ream.core.repository.voting.MemberRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.MemberService;
import com.ream.core.service.voting.dto.MemberDto;
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
public class VoterCandidateServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final MemberDtoMapper memberDtoMapper;

    @Autowired
    public VoterCandidateServiceImpl(MemberRepository memberRepository, MemberDtoMapper memberDtoMapper) {
        this.memberRepository = memberRepository;
        this.memberDtoMapper = memberDtoMapper;
    }

    @Override
    public MemberDto save(MemberDto memberDto) {
        return memberDtoMapper.toDto(memberRepository.save(memberDtoMapper.toEntity(memberDto)));
    }

    @Override
    public MemberDto update(MemberDto memberDto) {
        return memberDtoMapper.toDto(memberRepository.save(memberDtoMapper.toEntity(memberDto)));
    }

    @Override
    public PageResponse<MemberDto> findAll(PageRequest<MemberDto> model) {
        List<MemberDto> result = memberRepository
                .findAll(Pageable.ofSize(model.getPageSize())
                        .withPage(model.getCurrentPage() - 1))
                .stream().map(memberDtoMapper::toDto)
                .collect(Collectors.toList());
        long count = memberRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public List<MemberDto> findAll() {
        List<Member> list = memberRepository.findAll();
        if (list.size() > 0) {
            return memberDtoMapper.toDtoList(list);
        }
        return new ArrayList<>();
    }

    @Override
    public Optional<MemberDto> findById(UUID id) {
        Optional<Member> optionalAccessRole = memberRepository.findById(id);
        if (optionalAccessRole.isPresent()) {
            return Optional.ofNullable(
                    memberDtoMapper.toDto(optionalAccessRole.get()));
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
            return false;
        }
        memberRepository.deleteById(id);
        return true;
    }

}
*/
