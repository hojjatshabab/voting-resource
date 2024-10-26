package com.ream.core.service.baseInfo;

import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.dto.FileStorageDto;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileStorageService {



    FileStorageDto save(String recordId, MultipartFile file);


    FileStorageDto update(UUID id, MultipartFile file);

    PageResponse<FileStorageDto> findAll(PageRequest<FileStorageDto> model);


    Optional<FileStorageDto> getById(UUID id);


    Optional<FileStorageDto> getByFileCode(String fileCode);

    Resource getFileAsResource(String fileName);


    Boolean deleteById(UUID id);


    Optional<List<FileStorageDto>> getFilesDtoByByRecordId(String recordId);

}