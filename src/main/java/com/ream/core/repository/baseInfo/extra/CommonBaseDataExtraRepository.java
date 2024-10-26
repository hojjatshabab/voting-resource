package com.ream.core.repository.baseInfo.extra;




import com.ream.core.domain.baseInfo.Data;

import java.util.List;

public interface CommonBaseDataExtraRepository {
    List<com.ream.core.domain.baseInfo.Data> search(String pattern);
}
