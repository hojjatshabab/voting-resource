package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, UUID> {

    Optional<List<FileStorage>> findByRecordId(String recordId);

    Optional<FileStorage> findByFileCode(String fileCode);

    Optional<FileStorage> findByFileName(String fileName);

}
