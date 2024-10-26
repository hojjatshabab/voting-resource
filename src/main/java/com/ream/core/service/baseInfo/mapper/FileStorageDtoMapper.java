package com.ream.core.service.baseInfo.mapper;

import com.ream.core.domain.baseInfo.FileStorage;
import com.ream.core.service.BaseMapper;
import com.ream.core.service.baseInfo.dto.FileStorageDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface FileStorageDtoMapper extends BaseMapper<FileStorageDto, FileStorage> {
}
