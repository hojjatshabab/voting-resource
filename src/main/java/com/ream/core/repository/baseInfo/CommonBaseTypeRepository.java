package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CommonBaseTypeRepository extends JpaRepository<Type, Long> {
    Optional<Type> findByClassName(String className);

    Page<Type> findByClassNameContainingOrTitleContaining(String className, String title, Pageable pageable);

    Page<Type> findByClassNameContainingAndTitleContaining(String className, String title, Pageable pageable);

    Optional<List<Type>> findByTitleContains(String Title);
}
