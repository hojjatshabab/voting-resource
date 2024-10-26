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
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @Mapping(source = "commonBaseDataProvinceName", target = "commonBaseDataProvince.value")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "cityName", target = "city.name")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Member toEntity(MemberDto dto);

    @Override
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    MemberDto toDto(Member entity);

    @Override
    @Mapping(source = "commonBaseDataProvinceId", target = "commonBaseDataProvince.id")
    @Mapping(source = "commonBaseDataProvinceName", target = "commonBaseDataProvince.value")
    @Mapping(source = "cityId", target = "city.id")
    @Mapping(source = "cityName", target = "city.name")

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<Member> toEntityList(List<MemberDto> dtoList);

    @Override
    @Mapping(source = "commonBaseDataProvince.id", target = "commonBaseDataProvinceId")
    @Mapping(source = "commonBaseDataProvince.value", target = "commonBaseDataProvinceName")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    List<MemberDto> toDtoList(List<Member> entityList);


    @AfterMapping
    default void convertToNull(@MappingTarget Member member) {


        if (Objects.isNull(member.getCity()) || Objects.isNull(member.getCity().getId())) {
            member.setCity(null);

        }
        if (Objects.isNull(member.getCommonBaseDataProvince()) || Objects.isNull(member.getCommonBaseDataProvince().getId())) {
            member.setCommonBaseDataProvince(null);
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
