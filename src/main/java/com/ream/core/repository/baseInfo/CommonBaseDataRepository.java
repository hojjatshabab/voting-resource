package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.Data;
import com.ream.core.domain.baseInfo.Type;
import com.ream.core.repository.baseInfo.extra.CommonBaseDataExtraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CommonBaseDataRepository extends JpaRepository<Data, Long>, CommonBaseDataExtraRepository {
    Page<Data> findByCommonBaseType(Type commonBaseType, Pageable pageable);

    List<Data> findByCommonBaseTypeOrderByOrderNoAsc(Type commonBaseType);

    Optional<List<Data>> findByCommonBaseType(Type commonBaseType);

    Page<Data> findByValueContainsAndCommonBaseType(String value, Type commonBaseType, Pageable pageable);

    @Query(" select c from Data c ")
    List<Data> test();

}
