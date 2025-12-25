package com.artyom.jobtracker.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.artyom.jobtracker.dto.LoginUserDto;
import com.artyom.jobtracker.dto.RegisterUserDto;
import com.artyom.jobtracker.dto.UserResponseDto;
import com.artyom.jobtracker.entity.User;
import com.artyom.jobtracker.repository.UserRepository;
import com.artyom.jobtracker.security.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public UserResponseDto register(RegisterUserDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
    
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public String login(LoginUserDto dto) {
        User user = userRepository.findByEmail(dto.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtUtil.generateAccessToken(user.getName(), user.getEmail(), user.getRole() );
    }

    public String refreshAccessToken(String refreshToken) {

        User user = userRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        return jwtUtil.generateAccessToken(user.getName(), user.getEmail(), user.getRole());
    }
}