package com.ream.core.domain.baseInfo;

import com.ream.core.domain.AbstractAuditingEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ba_file_storage", schema = "base_info", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"file_code"})}
)

public class FileStorage extends AbstractAuditingEntity<UUID> {

    @Column(name = "files_path", nullable = false)
    @Comment("jwt")
    private String filesPath;
    @Column(name = "file_type", nullable = false)
    @Comment("jwt")
    private String fileType;
    @Column(name = "record_id")
    @Comment("jwt")
    private String recordId;
    @Column(name = "file_code", nullable = false)

    @Comment("jwt")
    private String fileCode;
    @Column(name = "file_name", nullable = false)
    @Comment("jwt")
    private String fileName;
    @Column(name = "size",nullable = false)
    @Comment("jwt")
    private Long size;

    public String getFilesPath() {
        return filesPath;
    }

    public void setFilesPath(String filesPath) {
        this.filesPath = filesPath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getFileCode() {
        return fileCode;
    }

    public void setFileCode(String fileCode) {
        this.fileCode = fileCode;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
