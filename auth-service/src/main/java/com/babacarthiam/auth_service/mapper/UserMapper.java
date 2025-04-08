package com.babacarthiam.auth_service.mapper;

import com.babacarthiam.auth_service.dto.RegisterRequestDTO;
import com.babacarthiam.auth_service.dto.RegisterResponseDTO;
import com.babacarthiam.auth_service.entity.User;

public class UserMapper {

    public static RegisterResponseDTO toRegisterResponseDTO(User user) {
        RegisterResponseDTO registerResponseDTO = new RegisterResponseDTO();
        registerResponseDTO.setId(user.getId());
        registerResponseDTO.setEmail(user.getEmail());
        registerResponseDTO.setRole(user.getRole());
        return registerResponseDTO;
    }

    public static User toUser(RegisterRequestDTO registerRequestDTO) {
        User user = new User();
        user.setEmail(registerRequestDTO.getEmail());
        user.setPassword(registerRequestDTO.getPassword());
        user.setRole(registerRequestDTO.getRole());
        return user;
    }
}
