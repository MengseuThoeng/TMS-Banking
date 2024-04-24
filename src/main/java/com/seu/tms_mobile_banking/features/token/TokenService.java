package com.seu.tms_mobile_banking.features.token;

import com.seu.tms_mobile_banking.features.auth.dto.AuthResponse;
import org.springframework.security.core.Authentication;

public interface TokenService {
    AuthResponse createToken(Authentication auth);
    String accessToken(Authentication auth);
    String refreshToken(Authentication auth);
}
