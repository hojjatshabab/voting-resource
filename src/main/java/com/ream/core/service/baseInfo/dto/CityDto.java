package com.ream.core.service.baseInfo.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private UUID id;
    private String name;
    private String code;
    private Long commonBaseDataProvinceId;
    private String commonBaseDataProvinceName;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getCommonBaseDataProvinceId() {
        return commonBaseDataProvinceId;
    }

    public void setCommonBaseDataProvinceId(Long commonBaseDataProvinceId) {
        this.commonBaseDataProvinceId = commonBaseDataProvinceId;
    }

    public String getCommonBaseDataProvinceName() {
        return commonBaseDataProvinceName;
    }

    public void setCommonBaseDataProvinceName(String commonBaseDataProvinceName) {
        this.commonBaseDataProvinceName = commonBaseDataProvinceName;
    }
}
