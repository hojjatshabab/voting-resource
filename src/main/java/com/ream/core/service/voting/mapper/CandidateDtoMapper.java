package com.ream.core.service.voting.mapper;

import com.ream.core.domain.voting.Candidate;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.CandidateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface CandidateDtoMapper extends BaseMapper<CandidateDto, Candidate> {
    @Override
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "votingId", target = "voting.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Candidate toEntity(CandidateDto dto);

    @Override
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.firstName", target = "memberName")
    @Mapping(source = "voting.id", target = "votingId")
    @Mapping(source = "voting.subject", target = "votingName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CandidateDto toDto(Candidate entity);

    @Override
    @Mapping(source = "memberId", target = "member.id")
    @Mapping(source = "votingId", target = "voting.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Candidate> toEntityList(List<CandidateDto> dtoList);

    @Override
    @Mapping(source = "member.id", target = "memberId")
    @Mapping(source = "member.firstName", target = "memberName")
    @Mapping(source = "voting.id", target = "votingId")
    @Mapping(source = "voting.subject", target = "votingName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<CandidateDto> toDtoList(List<Candidate> entityList);

}
