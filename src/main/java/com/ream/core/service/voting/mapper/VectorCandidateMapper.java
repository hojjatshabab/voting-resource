package com.ream.core.service.voting.mapper;

import com.ream.core.domain.voting.VoterCandidate;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.VoterCandidateDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VectorCandidateMapper extends BaseMapper<VoterCandidateDto, VoterCandidate> {
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoterCandidate toEntity(VoterCandidateDto dto);

  /*  @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.member.firstName", target = "candidateName")
    @Mapping(source = "voter.id", target = "voterId")
    @Mapping(source = "voter.member.firstName", target = "voterName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VoterCandidateDto toDto(VoterCandidate entity);

    @Override
    @Mapping(source = "candidateId", target = "candidate.id")
    @Mapping(source = "voterId", target = "voter.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<VoterCandidate> toEntityList(List<VoterCandidateDto> dtoList);

    @Override
    @Mapping(source = "candidate.id", target = "candidateId")
    @Mapping(source = "candidate.member.firstName", target = "candidateName")
    @Mapping(source = "voter.id", target = "voterId")
    @Mapping(source = "voter.member.firstName", target = "voterName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<VoterCandidateDto> toDtoList(List<VoterCandidate> entityList);*/
}
