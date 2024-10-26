package com.ream.core.service.baseInfo.impl;

import com.ream.core.domain.baseInfo.FileStorage;
import com.ream.core.repository.baseInfo.FileStorageRepository;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.FileStorageService;
import com.ream.core.service.baseInfo.dto.FileStorageDto;
import com.ream.core.service.baseInfo.mapper.FileStorageDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FileStorageServiceImpl implements FileStorageService {


    @Autowired
    FileStorageRepository fileStorageRepository;
    @Autowired
    FileStorageDtoMapper fileStorageDtoMapper;


    @Value("${spring.datasource.file.rootPath}")
    private String fileRootPath;

    @Value("${spring.datasource.file.directory}")
    private String filePath;
    private Path rootLocation;


    @Override
    public FileStorageDto save(String recordId, MultipartFile file) {
//        log.info("Save new file-storage {} in to database.", recordId);
        String fileName;
        try {


            String fileCode = randomAlphanumeric();
            Boolean isSave = fileSave(fileCode, file);
            if (isSave) {
                FileStorageDto fileDto = new FileStorageDto();
                fileDto.setRecordId(recordId);
                fileDto.setFileName(file.getOriginalFilename());
                fileDto.setFileType(file.getContentType());
                fileDto.setFileCode(fileCode);
                fileDto.setSize(file.getSize());
                fileDto.setFilesPath(getRootLocation().toString());
                return fileStorageDtoMapper.toDto(fileStorageRepository.save(fileStorageDtoMapper.toEntity(fileDto)));
            }
        } catch (Exception e) {
            return new FileStorageDto();
        }
        return new FileStorageDto();
    }


    @Override
    public FileStorageDto update(UUID id, MultipartFile file) {
//        log.info("update new file-storage {} in to database.", id);
        String fileName;
        Optional<FileStorage> optionalFileStorage = fileStorageRepository.findById(id);
        if (optionalFileStorage.isPresent()) {
            Boolean delete = fileDelete(optionalFileStorage.get().getFileCode());
            if (delete) {
                FileStorageDto fileDto = new FileStorageDto();
                fileDto.setId(optionalFileStorage.get().getId());
                fileDto.setRecordId(optionalFileStorage.get().getRecordId());
                fileDto.setFileName(file.getOriginalFilename());
                fileDto.setFileType(file.getContentType());
                fileDto.setFileCode(optionalFileStorage.get().getFileCode());
                fileDto.setFilesPath(getRootLocation().toString());
                fileDto.setSize(file.getSize());
                Boolean isSave = fileSave(optionalFileStorage.get().getFileCode(), file);
                if (isSave)
                    return fileStorageDtoMapper.toDto(fileStorageRepository.save(fileStorageDtoMapper.toEntity(fileDto)));
            }

        }
        return new FileStorageDto();
    }

    public String randomAlphanumeric() {
        int length = 8;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));

        }
        return randomString.toString();
    }


    @Override
    public PageResponse<FileStorageDto> findAll(PageRequest<FileStorageDto> model) {

//        log.info("Finding all file_storage.");
        List<FileStorageDto> result = fileStorageRepository.findAll(Pageable.ofSize(model.getPageSize()).withPage(model.getCurrentPage() - 1)).stream().map(fileStorageDtoMapper::toDto).collect(Collectors.toList());
        long count = fileStorageRepository.count();
        return new PageResponse<>(result, model.getPageSize(), count, model.getCurrentPage(), model.getSortBy());
    }

    @Override
    public Boolean deleteById(UUID id) {
        Optional<FileStorage> optionalFile = fileStorageRepository.findById(id);
        if (id == null || !optionalFile.isPresent()) {
//            log.info("Can not file_storage file by id {} .", id);
            return false;
        }
//        Boolean delete = fileDelete(optionalFile.get().getFileCode());
        if (true) {
            fileStorageRepository.deleteById(id);
//            log.info("Success delete find by id {} .", id);
            return true;
        }
        return false;
    }


    @Override
    public Optional<List<FileStorageDto>> getFilesDtoByByRecordId(String recordId) {
        Optional<List<FileStorage>> files = fileStorageRepository.findByRecordId(recordId);
        if (files.get().size() > 0) return Optional.ofNullable(fileStorageDtoMapper.toDtoList(files.get()));
        return Optional.empty();
    }

    @Override
    public Optional<FileStorageDto> getById(UUID id) {
        if (Objects.nonNull(id))
            return Optional.ofNullable(fileStorageDtoMapper.toDto(fileStorageRepository.findById(id).get()));
        return Optional.empty();
    }

    @Override
    public Optional<FileStorageDto> getByFileCode(String fileCode) {
        Optional<FileStorage> optionalFileStorage = fileStorageRepository.findByFileCode(fileCode);
        if (optionalFileStorage.isPresent())
            return Optional.ofNullable(fileStorageDtoMapper.toDto(optionalFileStorage.get()));
        return Optional.empty();
    }

    public Path getRootLocation() {
        if (this.rootLocation == null) this.rootLocation = Paths.get(fileRootPath.concat(filePath));
        return this.rootLocation;
    }

    public Path load(String filename) {
        return getRootLocation().resolve(filename);
    }

    public Boolean fileSave(String fileName, MultipartFile file) {
        try {
            String filename = StringUtils.cleanPath(fileName);
            if (file.isEmpty()) return Boolean.FALSE;
            if (filename.contains("..")) return Boolean.FALSE;

            // Creating directories
            File directory = getRootLocation().resolve(filename).toFile();
            if (!directory.exists()) {
                boolean result = directory.mkdirs();
                if (!result) return Boolean.FALSE;
            }

            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, getRootLocation().resolve(filename), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                return Boolean.FALSE;
            }
        } catch (Exception e) {
            return Boolean.FALSE;
        }

        return Boolean.TRUE;
    }


    @Override
    public Resource getFileAsResource(String fileName) {
        try {
            Optional<FileStorageDto> fileStorageDto = getByFileCode(fileName);
            if (!fileStorageDto.isPresent()) return null;
            Path file = load(fileName);
            Resource resource;
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) return resource;
            else return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }


    public Boolean fileDelete(String filename) {
        try {
            Path file = load(filename);
            Resource resource;
            resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                File tmpFile = file.toFile();
                return tmpFile.delete();
            } else return Boolean.FALSE;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

}
