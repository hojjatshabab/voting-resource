package com.ream.core.repository.baseInfo.extra;




import com.ream.core.domain.baseInfo.CommonBaseData;

import java.util.List;

public interface CommonBaseDataExtraRepository {
    List<CommonBaseData> search(String pattern);
}
