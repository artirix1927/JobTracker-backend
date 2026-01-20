package com.artyom.jobtracker.user;

import org.springframework.stereotype.Service;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TwoFactorAuthService {

    private final User2FaRepository user2FARepository;
    private final UserRepository userRepository;

    // ---------- EXISTING METHODS ----------

    // Enable 2FA → generate secret + QR
    public String enable2FA(Long userId) {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        User2Fa user2FA = user2FARepository.findById(user.getId())
                .orElse(new User2Fa());

        user2FA.setUser(user);
        user2FA.setSecret(key.getKey());
        user2FA.setEnabled(false); // set to true only after verification

        user2FARepository.save(user2FA);

        // QR code URL to scan in Google Authenticator
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(
                "JobTracker",
                user.getEmail(),
                key
        );
    }

    // Verify the code from user
    public boolean verifyCode(User user, int code) {
        User2Fa user2FA = user2FARepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("2FA not set up"));

        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        boolean isCodeValid = gAuth.authorize(user2FA.getSecret(), code);

        // if code is correct → enable 2FA
        if (isCodeValid && !user2FA.isEnabled()) {
            user2FA.setEnabled(true);
            user2FARepository.save(user2FA);
        }

        return isCodeValid;
    }

    public boolean is2FAEnabled(User user) {
        return user2FARepository.findById(user.getId())
                .map(User2Fa::isEnabled)
                .orElse(false);
    }

    // Generate a new secret key for a user
    public String generateSecret() {
        GoogleAuthenticator gAuth = new GoogleAuthenticator();
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey(); // just the raw secret
    }

    // Return a QR code URL for Google Authenticator app
    public String getQrCodeUrl(String email, String secret, String issuer) {
        GoogleAuthenticatorKey key = new GoogleAuthenticatorKey.Builder(secret).build();
        return GoogleAuthenticatorQRGenerator.getOtpAuthURL(issuer, email, key);
    }
}
