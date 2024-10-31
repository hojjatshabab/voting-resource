package com.ream.core.service.voting.mapper;

import com.ream.core.domain.voting.Voting;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.VotingDto;
import org.mapstruct.*;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface VotingDtoMapper extends BaseMapper<VotingDto, Voting> {
    @Override
    @Mapping(source = "performerId", target = "performer.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Voting toEntity(VotingDto dto);

    @Override
    @Mapping(source = "performer.id", target = "performerId")
    @Mapping(source = "performer.name", target = "performerName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VotingDto toDto(Voting entity);

    @Override
    @Mapping(source = "performerId", target = "performer.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Voting> toEntityList(List<VotingDto> dtoList);

    @Override
    @Mapping(source = "performer.id", target = "performerId")
    @Mapping(source = "performer.name", target = "performerName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<VotingDto> toDtoList(List<Voting> entityList);

}
