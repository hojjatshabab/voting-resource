package com.ream.core.service.security.dto;

import java.io.Serializable;
import java.util.List;

public class AccessAddressSerializable implements Serializable {

    private Boolean admin;
    private List<AccessAddressDto> accessAddressDtos;

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public List<AccessAddressDto> getAccessAddressDtos() {
        return accessAddressDtos;
    }

    public void setAccessAddressDtos(List<AccessAddressDto> accessAddressDtos) {
        this.accessAddressDtos = accessAddressDtos;
    }
}
