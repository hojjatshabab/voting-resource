package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.CommonBaseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CommonBaseTypeRepository extends JpaRepository<CommonBaseType, Long> {
    Optional<CommonBaseType> findByClassName(String className);

    Page<CommonBaseType> findByClassNameContainingOrTitleContaining(String className, String title, Pageable pageable);

    Page<CommonBaseType> findByClassNameContainingAndTitleContaining(String className, String title, Pageable pageable);

    Optional<List<CommonBaseType>> findByTitleContains(String Title);
}
