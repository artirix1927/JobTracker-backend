package com.artyom.jobtracker.user;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.artyom.jobtracker.controller.InvalidTwoFaCodeException;
import com.artyom.jobtracker.controller.TwoFaNotInitializedException;
import com.artyom.jobtracker.controller.TwoFaRequiredException;
import com.artyom.jobtracker.security.JwtUtil;
import com.warrenstrange.googleauth.GoogleAuthenticator;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TwoFactorAuthService twoFactorAuthService;
    private final User2FaRepository user2FaRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, 
                       JwtUtil jwtUtil, TwoFactorAuthService twoFactorAuthService, 
                       User2FaRepository user2FaRepository) {
        this.twoFactorAuthService = twoFactorAuthService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.user2FaRepository = user2FaRepository;
    }

    public UserResponseDto register(RegisterUserDto dto) {
        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
    
        User savedUser = userRepository.save(user);

        return new UserResponseDto(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public AuthResponseDto login(LoginUserDto dto) {
        User user = userRepository.findByEmail(dto.email())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        boolean is2FAEnabled = twoFactorAuthService.is2FAEnabled(user);

        if (is2FAEnabled) {
            // 2FA is enabled → do NOT generate tokens yet
            // just let frontend know it needs the code
            throw new TwoFaRequiredException(user.getEmail());
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);
        userRepository.save(user);

        return new AuthResponseDto(accessToken, refreshToken);

    }


    public AuthResponseDto verify2Fa(String userEmail, int code){
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!twoFactorAuthService.verifyCode(user, code)) {
            throw new RuntimeException("Invalid 2FA code");
        }

        String accessToken = jwtUtil.generateAccessToken(user);
        String refreshToken = jwtUtil.generateRefreshToken(user);
        user.setRefreshToken(refreshToken);

        userRepository.save(user);

        return new AuthResponseDto(accessToken, refreshToken);
    }

    public String refreshAccessToken(String refreshToken) {

        if (!jwtUtil.validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtUtil.getEmail(refreshToken);

        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // optional extra safety
        if (!refreshToken.equals(user.getRefreshToken())) {
            throw new RuntimeException("Refresh token mismatch");
        }

        return jwtUtil.generateAccessToken(user);
    }


    public Map<String,String> enable2Fa(User user){

        User2Fa user2FA = user2FaRepository.findById(user.getId())
            .orElseGet(() -> {
                User2Fa u = new User2Fa();
                u.setUser(user);
                return u;
            });

        if (user2FA.isEnabled()) {
            throw new IllegalStateException("2FA is already enabled");
        }

        if (user2FA.getSecret() == null) {
            String secret = twoFactorAuthService.generateSecret();
            user2FA.setSecret(secret);
        }

        user2FA.setEnabled(false);
        user2FaRepository.save(user2FA);

        String qrUrl = twoFactorAuthService.getQrCodeUrl(
            user.getEmail(),
            user2FA.getSecret(),
            "CareerHub"
        );

        return Map.of("qrUrl", qrUrl);
    }

    public boolean verify2FaSetup(User user, int code){
        User2Fa user2FA = user2FaRepository.findById(user.getId())
            .orElseThrow(TwoFaNotInitializedException::new);

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(user2FA.getSecret(), code);


        if (!isCodeValid) {
            throw new InvalidTwoFaCodeException();
        }

        user2FA.setEnabled(true);
        user2FaRepository.save(user2FA);

        return true;
    }
}