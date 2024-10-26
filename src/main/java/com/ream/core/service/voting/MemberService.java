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


    PageResponse<MemberDto> findAllNationalCode(String nationalCode,PageRequest<MemberDto> model);

    List<MemberDto> findAll();



    Optional<List<MemberDto>> findByNationalCodeContain(String nationalCode);

    Optional<MemberDto> findById(UUID id);


    Boolean deleteById(UUID id);

    Boolean finalityChoiceMemberById(UUID id);


    Optional<List<MemberDto>> getMembersByCurrentUser();

    Optional<List<MemberDto>> findAllMember(UUID memberId);



}
