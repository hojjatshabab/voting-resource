package com.ream.core.service.security.dto;

import lombok.*;

import java.util.UUID;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }


    private Boolean passwordMustChange;

    public Boolean getPasswordMustChange() {
        return passwordMustChange;
    }

    public void setPasswordMustChange(Boolean passwordMustChange) {
        this.passwordMustChange = passwordMustChange;
    }

    private String firstName;
    private String lastName;
    private String username;
    private String password;

    private Boolean enabledUser;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public Boolean getEnabledUser() {
        return enabledUser;
    }

    public void setEnabledUser(Boolean enabledUser) {
        this.enabledUser = enabledUser;
    }
}
