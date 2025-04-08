package com.babacarthiam.auth_service.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponseDTO {

    private String id;
    private String email;
    private String role;
}
