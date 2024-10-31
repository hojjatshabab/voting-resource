package com.ream.core.service.voting.mapper;

import com.ream.core.domain.voting.Candidate;
import com.ream.core.domain.voting.Voter;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.CandidateDto;
import com.ream.core.service.voting.dto.VoterDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VoterDtoMapper extends BaseMapper<VoterDto, Voter> {
    @Override
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "votingId", target = "voting.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voter toEntity(VoterDto dto);

    @Override
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.firstName", target = "memberName")
    @Mapping(source = "voting.id", target = "votingId")
    @Mapping(source = "voting.subject", target = "votingName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoterDto toDto(Voter entity);

    @Override
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "votingId", target = "voting.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Voter> toEntityList(List<VoterDto> dtoList);

    @Override
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.firstName", target = "memberName")
    @Mapping(source = "voting.id", target = "votingId")
    @Mapping(source = "voting.subject", target = "votingName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<VoterDto> toDtoList(List<Voter> entityList);

}
