package com.babacarthiam.auth_service.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.babacarthiam.auth_service.dto.LoginRequestDTO;
import com.babacarthiam.auth_service.dto.RegisterRequestDTO;
import com.babacarthiam.auth_service.dto.RegisterResponseDTO;
import com.babacarthiam.auth_service.entity.User;
import com.babacarthiam.auth_service.exception.EmailAlreadyExistsException;
import com.babacarthiam.auth_service.mapper.UserMapper;
import com.babacarthiam.auth_service.repository.UserRepository;
import com.babacarthiam.auth_service.util.JwtUtil;

import io.jsonwebtoken.JwtException;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, UserService userService, PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public RegisterResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        if (userService.findByEmail(registerRequestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(
                    "A user with this email already exists" + registerRequestDTO.getEmail());
        }

        registerRequestDTO.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
        User user = userRepository.save(UserMapper.toUser(registerRequestDTO));

        return UserMapper.toRegisterResponseDTO(user);
    }

    public Optional<String> authenticate(LoginRequestDTO loginRequestDTO) {
        Optional<String> token = userService.findByEmail(loginRequestDTO.getEmail())
                .filter(u -> passwordEncoder.matches(loginRequestDTO.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRole()));

        return token;
    }

    public boolean validateToken(String token) {
        try {
            jwtUtil.validateToken(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

}
