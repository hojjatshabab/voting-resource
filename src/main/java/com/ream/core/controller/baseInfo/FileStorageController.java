package com.ream.core.controller.baseInfo;


import com.ream.core.controller.BaseController;
import com.ream.core.model.ModeType;
import com.ream.core.controller.ActionResult;
import com.ream.core.service.PageRequest;
import com.ream.core.service.PageResponse;
import com.ream.core.service.baseInfo.FileStorageService;
import com.ream.core.service.baseInfo.dto.FileStorageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.util.*;

@RestController
@RequestMapping("api/file-storages")
@RequiredArgsConstructor
@Tag(name = "ذخیره و اپلود فایل", description = "ذخیره و اپلود فایل")
public class FileStorageController extends BaseController {

    @Autowired
    FileStorageService fileStorageService;


    @Autowired
    private MessageSource messageSource;

    @PostMapping("upload-file/{recordId}")
    @Operation(summary = "upload file (باگزاری فایل)", description = "حتما پارامتر file که از نوع MultipartFile است را بارگزاری و همچنین recordId هر موجودیت که ذخیره شده است به این فرمت ارسال گردد ، return ActionResult<FileStorageDto")

    public ActionResult<FileStorageDto> save(@PathVariable String recordId, @RequestParam("file") MultipartFile file, Locale locale) {
        isExist(null, recordId, ModeType.CREATE, file, locale);
        if (Objects.isNull(file))
            return CONFLICT("file is empty", locale);
        try {
            return RESULT(fileStorageService.save(recordId, file),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("recordId", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }


    @PutMapping("upload-file/{id}")
    @Operation(summary = "upload file (ویرایش فایل هایی که قبلا بارگزاری شده است)", description = "حتما پارامتر file که از نوع MultipartFile است را بارگزاری و همچنین id که در FileStorageDto  ذخیره شده است به این فرمت ارسال گردد ، return ActionResult<FileStorageDto")

    public ActionResult<FileStorageDto> update(@PathVariable UUID id, @RequestParam("file") MultipartFile file, Locale locale) {
        isExist(id, null, ModeType.EDIT, file, locale);
        if (Objects.isNull(file)) return CONFLICT("file is empty", locale);
        try {
            return RESULT(fileStorageService.update(id, file),locale);
        } catch (DataIntegrityViolationException exception) {
            return CONFLICT("recordId", locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    @GetMapping
    @Operation(summary = "لیست تمام فایل های بارگزاری شده", description = "ActionResult<PageResponse<FileStorageDto>>")
    public ActionResult<PageResponse<FileStorageDto>> findAll(
            @RequestParam int currentPage,
            @RequestParam int pageSize,
            Locale locale) {
        if (currentPage <= 0 || pageSize <= 0) {
            return NOT_ACCEPTABLE(" { currentPage == 0 || pageSize == 0 } ", locale);
        }
        PageRequest<FileStorageDto> request = new PageRequest<>();
        request.setPageSize(pageSize);
        request.setCurrentPage(currentPage);
        PageResponse<FileStorageDto> fileDtoPageResponse;
        try {
            fileDtoPageResponse = fileStorageService.findAll(request);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (Objects.isNull(fileDtoPageResponse)) {
            return NO_CONTENT("FileStorage", locale);
        } else {
            return RESULT(fileDtoPageResponse,locale);
        }
    }

    @GetMapping("/id/{id}")
    @Operation(summary = "جستجو براساس id ->FileStorageDto", description = "ActionResult<Optional<FileStorageDto>>")
    public ActionResult<Optional<FileStorageDto>> findById(@PathVariable UUID id, Locale locale) {
        Optional<FileStorageDto> optionalFileDto;
        if (id.equals(null)) {
            return NO_CONTENT(" id= " + id, locale);
        }
        try {
            optionalFileDto = fileStorageService.getById(id);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalFileDto.isPresent()) {
            return RESULT(optionalFileDto,locale);
        } else {
            return NOT_FOUND(" id= " + id, locale);
        }
    }


    @GetMapping("/find-all-file-by-record-id/{recordId}")
    @Operation(summary = "لیست تمام فایل های ذخیره شده یک رکورد", description = "ActionResult<Optional<List<FileStorageDto>>> ، حتما recordId موجودیت باشد داشته باشد")
    public ActionResult<Optional<List<FileStorageDto>>> findAllFileByRecordId(@PathVariable String recordId, Locale locale) {
        Optional<List<FileStorageDto>> optionalFilesDto;
        if (recordId.equals(null)) {
            return NO_CONTENT(" id= " + recordId, locale);
        }
        try {
            optionalFilesDto = fileStorageService.getFilesDtoByByRecordId(recordId);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
        if (optionalFilesDto.isPresent()) {
            return RESULT(optionalFilesDto,locale);
        } else {
            return RESULT(Optional.empty(),locale);
        }
    }


    @GetMapping("/download-file-by-file-code/{fileCode}")
    @Operation(summary = "دانلود فایل های ذخیره شده بر اساس fileCode", description = "بازگشت این فانکشن یک پاسخ html است که در ان فایل موجو است")

    public ResponseEntity<?> downloadFileByFileCode(@PathVariable String fileCode, Locale locale) {

        Resource resource = null;
        try {
            resource = fileStorageService.getFileAsResource(fileCode);
            if (resource == null) {
                String message = messageSource.getMessage("error.notfound", null, locale);
                return new ResponseEntity<>(message + "{ file not found } ", HttpStatus.NOT_FOUND);
            }
            Optional<FileStorageDto> optionalFileStorageDto = fileStorageService.getByFileCode(fileCode);
            String contentType = optionalFileStorageDto.get().getFileType();
            String headerValue = "attachment; filename=\"" + optionalFileStorageDto.get().getFileName() + "\"";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                    .body(resource);
        } catch (Exception exception) {
            return new ResponseEntity<>(exception.getMessage().toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


    @GetMapping("/download-file-by-file-id/{id}")
    @Operation(summary = "دانلود فایل های ذخیره شده بر اساس  FileStorageDto->id", description = "بازگشت این فانکشن یک پاسخ html است که در ان فایل موجو است")

    public ResponseEntity<?> downloadFileByFileId(@PathVariable UUID id, Locale locale) {
        Optional<FileStorageDto> optionalFileDto;
        if (id.equals(null)) {
            return new ResponseEntity<>("ID_NO_CONTENT", HttpStatus.NO_CONTENT);
        }
        try {
            optionalFileDto = fileStorageService.getById(id);
        } catch (Exception exception) {
            return new ResponseEntity<>("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (optionalFileDto.isPresent()) {
            return downloadFileByFileCode(optionalFileDto.get().getFileCode(), locale);
        } else {
            return new ResponseEntity<>("File not found", HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "حذف فایل براساس FileStorageDto->id", description = "بازگشت این فانکشن یک پاسخ html است که در ان فایل موجو است")
    public ActionResult<Boolean> delete(@PathVariable UUID id, Locale locale) {
        if (id.equals(null)) {
            return NO_CONTENT("id", locale);
        }
        if (!fileStorageService.getById(id).isPresent()) {
            return NOT_FOUND("id =" + id, locale);
        }
        try {
            return RESULT(fileStorageService.deleteById(id),locale);
        } catch (Exception exception) {
            return INTERNAL_SERVER_ERROR(exception.getMessage(), locale);
        }
    }

    private void isExist(UUID id, String recordId, ModeType modeType, MultipartFile file, Locale locale) {

        if (modeType.equals(ModeType.CREATE)) {
            isExistByStringId(recordId, locale);
            Optional<List<FileStorageDto>> optionalFilesDto = fileStorageService.getFilesDtoByByRecordId(recordId);
            if (!optionalFilesDto.isEmpty())
                for (FileStorageDto dto : optionalFilesDto.get()) {
                    if (dto.getFileName().equals(file.getOriginalFilename()))
                        CONFLICT("The file is available by recordId ".concat(recordId.toString()), locale);
                }

        }
        if (modeType.equals(ModeType.EDIT)) {
            isExistByUUIDId(id, locale);
            Optional<FileStorageDto> optionalFileDto = fileStorageService.getById(id);
            if (!optionalFileDto.isPresent())
                CONFLICT("id", locale);
            else {
                Optional<List<FileStorageDto>> optionalFilesDto = fileStorageService.getFilesDtoByByRecordId(optionalFileDto.get().getRecordId());
                if (!optionalFilesDto.isEmpty())
                for (FileStorageDto dto : optionalFilesDto.get()) {
                    if (dto.getFileName().equals(file.getOriginalFilename()))
                        CONFLICT("The file is available by id ".concat(id.toString()), locale);
                }

            }
        }

    }

    private void isExistByStringId(String id, Locale locale) {
        if (Objects.isNull(id) || id.isEmpty() || id.isBlank()) {
            NO_CONTENT("id =" + id.toString(), locale);
        }
    }

    private void isExistByUUIDId(UUID id, Locale locale) {
        if (Objects.isNull(id)) {
            NO_CONTENT("id =" + id.toString(), locale);
        }
    }


}
