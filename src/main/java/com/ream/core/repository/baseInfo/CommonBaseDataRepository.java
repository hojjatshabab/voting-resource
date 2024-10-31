package com.ream.core.repository.baseInfo;

import com.ream.core.domain.baseInfo.CommonBaseData;
import com.ream.core.domain.baseInfo.CommonBaseType;
import com.ream.core.repository.baseInfo.extra.CommonBaseDataExtraRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface CommonBaseDataRepository extends JpaRepository<CommonBaseData, Long>, CommonBaseDataExtraRepository {
    Page<CommonBaseData> findByCommonBaseType(CommonBaseType commonBaseType, Pageable pageable);

    List<CommonBaseData> findByCommonBaseTypeOrderByOrderNoAsc(CommonBaseType commonBaseType);

    Optional<List<CommonBaseData>> findByCommonBaseType(CommonBaseType commonBaseType);

    Page<CommonBaseData> findByValueContainsAndCommonBaseType(String value, CommonBaseType commonBaseType, Pageable pageable);

    @Query(" select c from CommonBaseData c ")
    List<CommonBaseData> test();

}
