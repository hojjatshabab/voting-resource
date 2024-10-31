package com.ream.core.service.voting.mapper;

import com.ream.core.domain.voting.Member;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.voting.dto.MemberDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Objects;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface MemberDtoMapper extends BaseMapper<MemberDto, Member> {
    @Override
    @Mapping(source = "employmentPlaceId", target = "employmentPlace.id")
    @Mapping(source = "residencePlaceId", target = "residencePlace.id")
    @Mapping(source = "birthPlaceId", target = "birthPlace.id")
    @Mapping(source = "performerId", target = "performer.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Member toEntity(MemberDto dto);

    @Override
    @Mapping(source = "employmentPlace.id", target = "employmentPlaceId")
    @Mapping(source = "employmentPlace.name", target = "employmentPlaceName")
    @Mapping(source = "residencePlace.id", target = "residencePlaceId")
    @Mapping(source = "residencePlace.name", target = "residencePlaceName")
    @Mapping(source = "birthPlace.id", target = "birthPlaceId")
    @Mapping(source = "birthPlace.name", target = "birthPlaceName")
    @Mapping(source = "performer.id", target = "performerId")
    @Mapping(source = "performer.name", target = "performerName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MemberDto toDto(Member entity);

    @Override
    @Mapping(source = "employmentPlaceId", target = "employmentPlace.id")
    @Mapping(source = "residencePlaceId", target = "residencePlace.id")
    @Mapping(source = "birthPlaceId", target = "birthPlace.id")
    @Mapping(source = "performerId", target = "performer.id")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Member> toEntityList(List<MemberDto> dtoList);

    @Override
    @Mapping(source = "employmentPlace.id", target = "employmentPlaceId")
    @Mapping(source = "employmentPlace.name", target = "employmentPlaceName")
    @Mapping(source = "residencePlace.id", target = "residencePlaceId")
    @Mapping(source = "residencePlace.name", target = "residencePlaceName")
    @Mapping(source = "birthPlace.id", target = "birthPlaceId")
    @Mapping(source = "birthPlace.name", target = "birthPlaceName")
    @Mapping(source = "performer.id", target = "performerId")
    @Mapping(source = "performer.name", target = "performerName")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MemberDto> toDtoList(List<Member> entityList);

    @AfterMapping
    default void convertToNull(@MappingTarget Member member) {
        if (Objects.isNull(member.getBirthPlace()) || Objects.isNull(member.getBirthPlace().getId())) {
            member.setBirthPlace(null);
        }
        if (Objects.isNull(member.getEmploymentPlace()) || Objects.isNull(member.getEmploymentPlace().getId())) {
            member.setEmploymentPlace(null);
        }
        if (Objects.isNull(member.getResidencePlace()) || Objects.isNull(member.getResidencePlace().getId())) {
            member.setResidencePlace(null);
        }
        if (Objects.isNull(member.getPerformer()) || Objects.isNull(member.getPerformer().getId())) {
            member.setPerformer(null);
        }
    }
   /* @AfterMapping
    default void convertToNull(@MappingTarget MemberDto memberDto) {
        if (Objects.isNull(memberDto.getFinalityChoice()))
            memberDto.setFinalityChoice(false);
        if (Objects.isNull(memberDto.getConfirmSelection()))
            memberDto.setFinalityChoice(false);
        if (Objects.nonNull(memberDto.getPreviousUnitPrice())) {
            try {
                BigDecimal decimal = new BigDecimal(memberDto.getPreviousUnitPrice());

//                Double number = Double.valueOf();
                memberDto.setUnitExpertValue( decimal.subtract(new BigDecimal("12000000000")).toString());

                BigDecimal decimalH = new BigDecimal(memberDto.getUnitExpertValue());

                memberDto.setUnitExpertValueH( decimalH.add(new BigDecimal("8600000000")).toString());


//                memberDto.setUnitExpertValue(String.valueOf(decimal - (12000000000)));
            } catch (Exception e) {
                System.out.println(e.getMessage() + "error PreviousUnitPrice number");
            }
        }
    }*/

}
