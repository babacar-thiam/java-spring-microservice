package com.babacarthiam.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private final String token;

    public LoginResponseDTO(String token) {
        this.token = token;
    }
}
