package com.ream.core.service.baseInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDto {
    private Long id;
    private String key;
    private String value;
    private Integer orderNo;
    private Boolean active;
    private String description;
    private Long commonBaseTypeId;
    private String commonBaseTypeName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCommonBaseTypeId() {
        return commonBaseTypeId;
    }

    public void setCommonBaseTypeId(Long commonBaseTypeId) {
        this.commonBaseTypeId = commonBaseTypeId;
    }

    public String getCommonBaseTypeName() {
        return commonBaseTypeName;
    }

    public void setCommonBaseTypeName(String commonBaseTypeName) {
        this.commonBaseTypeName = commonBaseTypeName;
    }
}
