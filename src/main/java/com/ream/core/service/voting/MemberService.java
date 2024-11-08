package com.ream.core.service.voting;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.voting.dto.MemberDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MemberService {
    MemberDto save(MemberDto memberDto);

    MemberDto update(MemberDto memberDto);

    PageResponse<MemberDto> findAll(PageRequest<MemberDto> model);

    List<MemberDto> findAll();

    Optional<MemberDto> findById(UUID id);

    Boolean deleteById(UUID id);

}
