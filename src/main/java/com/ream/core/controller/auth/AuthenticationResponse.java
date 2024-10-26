package com.ream.core.controller.auth;

import org.springframework.beans.factory.annotation.Value;

public class AuthenticationResponse {


    public AuthenticationResponse(String token,Long expireToken,Integer status,String refreshToken) {
        this.token = token;
        this.expireToken = expireToken;
        this.status = status;
        this.refreshToken=refreshToken;
    }

    public String token;


    public Integer status;


    public String refreshToken;


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Long expireToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpireToken() {
        return expireToken;
    }

    public void setExpireToken(Long expireToken) {
        this.expireToken = expireToken;
    }
}
