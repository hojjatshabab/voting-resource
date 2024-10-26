package com.ream.core.service.baseInfo.impl;

import com.ream.core.domain.baseInfo.OrganizationUnit;
import com.ream.core.repository.baseInfo.OrganizationUnitRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.OrganizationUnitService;
import com.ream.core.service.baseInfo.dto.OrganizationUnitDto;
import com.ream.core.service.baseInfo.mapper.OrganizationUnitDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Transactional
@Service
@Slf4j
@RequiredArgsConstructor
public class OrganizationUnitServiceImpI implements OrganizationUnitService {
    @Autowired
    OrganizationUnitRepository organizationUnitRepository;
    @Autowired
    OrganizationUnitDtoMapper organizationUnitDtoMapper;

    @Override
    public OrganizationUnitDto save(OrganizationUnitDto organizationUnitDto) {
//        log.info("Save new organizationUnitDto {} in to database.", organizationUnitDto.getName());

        String gName = generateCompleteName(organizationUnitDto);
        organizationUnitDto.setCompleteName(gName.substring(0, gName.length() - 1));
        organizationUnitDto.setCodePath(generateCodePath(organizationUnitDto));
        return setChildrenOrganizationDto(organizationUnitDtoMapper.toDto(organizationUnitRepository.save(organizationUnitDtoMapper.toEntity(organizationUnitDto))));
    }



    @Override
    public OrganizationUnitDto getCurrentOrganizationUnit() {
//        UUID currentOrganization = authorizationHeaderUtil.getOrgUser();
//        if (currentOrganization != null) {
//            Optional<OrganizationUnit> unit = organizationUnitRepository.findById(currentOrganization);
//            if (unit.isPresent()) return organizationUnitDtoMapper.toDto(unit.get());
//        }
        return null;
    }





    @Override
    public Optional<OrganizationUnitDto> findParentByOrgId(UUID id) {
        if (Objects.nonNull(id)) {
            Optional<OrganizationUnit> unit = organizationUnitRepository.findById(id);
            if (unit.isPresent())
                if (Objects.nonNull(unit.get().getParent()))
                    return Optional.ofNullable(organizationUnitDtoMapper.toDto(unit.get().getParent()));
        }
        return Optional.empty();
    }


    @Override
    public OrganizationUnitDto update(OrganizationUnitDto organizationUnitDto) {
//        log.info("Update organizationUnitDto {} in to database.", organizationUnitDto.getName());
        return setChildrenOrganizationDto(organizationUnitDtoMapper.toDto(organizationUnitRepository.save(organizationUnitDtoMapper.toEntity(organizationUnitDto))));
    }

    @Override
    public PageResponse<OrganizationUnitDto> findAll(PageRequest<OrganizationUnitDto> model) {
//        log.info("Finding all organizationUnitDto.");
        List<OrganizationUnitDto> result = organizationUnitRepository.findAll(
//                      Example.of(example, ExampleMatcher.matching().withMatcher("title", ExampleMatcher.GenericPropertyMatchers.contains())),
                Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(organizationUnitDtoMapper::toDto).collect(Collectors.toList());
        long count = organizationUnitRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public OrganizationUnitDto findByParentIdIsNull() {
        return setChildrenOrganizationDto(organizationUnitDtoMapper.toDto(organizationUnitRepository.findByParentIdIsNull()));
    }

    @Override
    public Optional<OrganizationUnitDto> findById(UUID id) {
//        log.info("Finding organizationUnitDto by id {} .", id);
        Optional<OrganizationUnit> organizationUnitDtoOptional = organizationUnitRepository.findById(id);
        if (organizationUnitDtoOptional.isPresent()) {
            return Optional.ofNullable(setChildrenOrganizationDto(organizationUnitDtoMapper.toDto(organizationUnitDtoOptional.get())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<OrganizationUnitDto> findByName(String name) {
//        log.info("Finding OrganizationUnitDto by name {} .", name);
        Optional<OrganizationUnit> organizationUnitOptional = organizationUnitRepository.findByName(name);
        if (organizationUnitOptional.isPresent()) {
            return Optional.ofNullable(setChildrenOrganizationDto(organizationUnitDtoMapper.toDto(organizationUnitOptional.get())));
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<OrganizationUnitDto>> findByCode(String code) {
//        log.info("Finding OrganizationUnitDto by code {} .", code);
        Optional<List<OrganizationUnit>> organizationUnitOptional = organizationUnitRepository.findByCode(code);
        if (organizationUnitOptional.isPresent()) {
            List<OrganizationUnitDto> unitsDto = organizationUnitDtoMapper.toDtoList(organizationUnitOptional.get());
            List<OrganizationUnitDto> returnList = new ArrayList<>();
            for (OrganizationUnitDto dto : unitsDto)
                returnList.add(setChildrenOrganizationDto(dto));
            return Optional.ofNullable(returnList);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<OrganizationUnitDto>> findByNameContains(String name) {
        Optional<List<OrganizationUnit>> organizationUnit = organizationUnitRepository.findByNameContains(name);
        if (organizationUnit.get().size() > 0)
            return Optional.ofNullable(setChildrenOrganizationDto(organizationUnitDtoMapper.toDtoList(organizationUnit.get())));
        return Optional.empty();
    }


    @Override
    public Optional<List<OrganizationUnitDto>> findAllParentByOrgId(UUID id) {
        List<OrganizationUnitDto> organizationUnitDtos = new ArrayList<>();
        while (Objects.nonNull(id) && findParentByOrgId(id).isPresent()) {
            OrganizationUnitDto dto = findById(findParentByOrgId(id).get().getId()).get();
            organizationUnitDtos.add(dto);
            if (Objects.nonNull(dto.getId()))
                id = dto.getId();
            else id = null;
        }
        return Optional.ofNullable(organizationUnitDtos);

    }

    @Override
    public Optional<List<OrganizationUnitDto>> findAllChildrenById(UUID id) {
//        log.info("Finding OrganizationUnitDto by id {} .", id);
        Optional<OrganizationUnit> organizationUnitOptional = organizationUnitRepository.findById(id);
        Optional<List<OrganizationUnitDto>> organizationUnitOptionalList = findAllChildrenByCodePath(organizationUnitOptional.get().getCodePath());
        if (organizationUnitOptionalList.isPresent()) {
            return organizationUnitOptionalList;
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<OrganizationUnitDto>> findAllChildrenByCodePath(String codePath) {
//        log.info("Finding OrganizationUnitDto by codePath {} .", codePath);
        Optional<List<OrganizationUnit>> organizationUnitOptionalList = organizationUnitRepository.findAllChildrenByCodePath(codePath.concat("%"));
        if (organizationUnitOptionalList.isPresent()) {
            List<OrganizationUnitDto> unitsDto = organizationUnitDtoMapper.toDtoList(organizationUnitOptionalList.get());
            List<OrganizationUnitDto> returnList = new ArrayList<>();
            for (OrganizationUnitDto dto : unitsDto)
                returnList.add(setChildrenOrganizationDto(dto));
            return Optional.ofNullable(returnList);
        }
        return Optional.empty();
    }

    @Override
    public Boolean deleteById(UUID id) {
        if (id == null || !findById(id).isPresent()) {
//            log.info("Can not find OrganizationUnitDto by id {} .", id);
            return false;
        }
        organizationUnitRepository.deleteById(id);
//        log.info("Success delete OrganizationUnitDto by id {} .", id);
        return true;
    }

    @Override
    public String generateCompleteName(OrganizationUnitDto organizationUnitDto) {
        if (!Objects.nonNull(organizationUnitDto.getParentId()) || !Objects.nonNull(organizationUnitDto.getName()))
            return "";
        if (Objects.nonNull(organizationUnitDto.getId()))
            if (organizationUnitDto.getId().equals(UUID.fromString("0d04850c-5bf5-47c3-8dfe-67340e934c90")))
                return null;

        StringBuilder builder = new StringBuilder(organizationUnitDto.getName().concat("-").concat(generateCompleteName(findById(organizationUnitDto.getParentId()).get())));

        return builder.toString();
    }


    @Override
    public String generateCodePath(OrganizationUnitDto organizationUnitDto) {
        if (Objects.nonNull(organizationUnitDto.getId()))
            if (organizationUnitDto.getId().equals(UUID.fromString("0d04850c-5bf5-47c3-8dfe-67340e934c90")))
                return null;
        if (!Objects.nonNull(organizationUnitDto.getParentId())) return null;
        return organizationUnitRepository.findUniqueCodePathByParentCodePath(findById(organizationUnitDto.getParentId()).get().getCodePath());

    }

    @Override
    public Optional<List<OrganizationUnitDto>> findChildrenById(UUID id) {
//        log.info("Finding findChildrenById {} .", id);
        Optional<OrganizationUnit> organizationUnitOptional = organizationUnitRepository.findById(id);
        if (organizationUnitOptional.isPresent()) {
            List<OrganizationUnitDto> unitsDto = organizationUnitDtoMapper.toDtoList(organizationUnitRepository.findByParentIdOrderByCodePathAsc(organizationUnitOptional.get().getId()).get());
            List<OrganizationUnitDto> returnList = new ArrayList<>();
            for (OrganizationUnitDto dto : unitsDto)
                returnList.add(setChildrenOrganizationDto(dto));

            return Optional.ofNullable(returnList);
        }
        return Optional.empty();
    }

    @Override
    public Optional<List<OrganizationUnitDto>> findChildrenByCodePath(String codePath) {
//        log.info("Finding findChildrenById {} .", codePath);
        Optional<OrganizationUnit> organizationUnitOptional = organizationUnitRepository.findByCodePath(codePath);
        if (organizationUnitOptional.isPresent()) {
            List<OrganizationUnitDto> unitsDto = organizationUnitDtoMapper.toDtoList(organizationUnitRepository.findByParentIdOrderByCodePathAsc(organizationUnitOptional.get().getId()).get());
            List<OrganizationUnitDto> returnList = new ArrayList<>();
            for (OrganizationUnitDto dto : unitsDto)
                returnList.add(setChildrenOrganizationDto(dto));
            return Optional.ofNullable(returnList);
        }
        return Optional.empty();
    }

    @Override
    public String generateCodePathByParentId(OrganizationUnitDto organizationUnitDto) {
        if (Objects.nonNull(organizationUnitDto)) {
            if (organizationUnitDto.getId() != null) {
                Optional<List<OrganizationUnit>> units = organizationUnitRepository.findByParentIdOrderByCodePathDesc(organizationUnitDto.getId());
                if (units.get().size() > 0) {
                    Integer intCodePath = Integer.valueOf(units.get().get(0).getCodePath().trim()) + 1;
                    return "00".concat(intCodePath.toString());
                } else return organizationUnitDto.getCodePath().concat("001");
            } else {
                Optional<List<OrganizationUnit>> units = organizationUnitRepository.findAllChildrenByCodePath(organizationUnitDto.getCodePath().concat("%"));

                if (units.isPresent()) {
                    Integer intCodePath = Integer.valueOf(units.get().get(units.get().size() - 1).getCodePath().trim()) + 1;
                    return "00".concat(intCodePath.toString());
                } else return organizationUnitDto.getCodePath().concat("001");

            }
        }
        return null;
    }

    @Override
    public Optional<List<OrganizationUnitDto>> changeNodeByParentId(String parentId, OrganizationUnitDto organizationUnitDto) {
        if (!findById(organizationUnitDto.getId()).isPresent()) {
//            log.info("Finding changeNodeByParentId {} .", organizationUnitDto.getId());
            return Optional.empty();
        }
        String codePathOld = organizationUnitDto.getCodePath();
        organizationUnitDto.setParentId(UUID.fromString(parentId));
        String gName = generateCompleteName(organizationUnitDto);
        organizationUnitDto.setCompleteName(gName.substring(0, gName.length() - 1));
        organizationUnitDto.setCodePath(generateCodePathByParentId(findById(organizationUnitDto.getParentId()).get()));
        OrganizationUnitDto organizationUnitDtoNew = update(organizationUnitDto);
        Optional<List<OrganizationUnitDto>> organizationUnitDtoChild = findAllChildrenByCodePath(codePathOld);
        if (organizationUnitDtoChild.isPresent()) {
            for (OrganizationUnitDto dto : organizationUnitDtoChild.get()) {
                String newCodePath = dto.getCodePath();
                dto.setCodePath(newCodePath.replaceFirst(codePathOld, organizationUnitDtoNew.getCodePath()));
                String gNameDto = generateCompleteName(dto);
                dto.setCompleteName(gNameDto.substring(0, gName.length() - 1));
                update(dto);
            }
        }
        List<OrganizationUnitDto> unitsDto = organizationUnitRepository.findAll().stream().map(organizationUnitDtoMapper::toDto).collect(Collectors.toList());
        List<OrganizationUnitDto> returnList = new ArrayList<>();
        for (OrganizationUnitDto dto : unitsDto)
            returnList.add(setChildrenOrganizationDto(dto));
        return Optional.ofNullable(returnList);
    }

    private OrganizationUnitDto setChildrenOrganizationDto(OrganizationUnitDto organizationUnitDto) {
        if (organizationUnitDto.getId() != null) {
            Optional<List<OrganizationUnit>> childrenOrg = organizationUnitRepository.findByParentIdOrderByCodePathAsc(organizationUnitDto.getId());
            if (childrenOrg.get().size() > 0) {
                List<OrganizationUnitDto> organizationUnitsDto = organizationUnitDtoMapper.toDtoList(childrenOrg.get());
                organizationUnitDto.setChildren(organizationUnitsDto);
                for (OrganizationUnitDto dto : organizationUnitsDto) {
                    setChildrenOrganizationDto(dto);
                }
            } else organizationUnitDto.setChildren(null);
            return organizationUnitDto;
        }
        return null;

    }

    private List<OrganizationUnitDto> setChildrenOrganizationDto(List<OrganizationUnitDto> organizationUnitDto) {
        List<OrganizationUnitDto> returnList = new ArrayList<>();
        if (organizationUnitDto != null && organizationUnitDto.size() > 0)
            for (OrganizationUnitDto dto : organizationUnitDto) {
                returnList.add(setChildrenOrganizationDto(dto));
            }
        return returnList;
    }

}

