package com.seu.tms_mobile_banking.features.auth;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.features.auth.dto.AuthResponse;
import com.seu.tms_mobile_banking.features.auth.dto.ChangePasswordRequest;
import com.seu.tms_mobile_banking.features.auth.dto.LoginRequest;
import com.seu.tms_mobile_banking.features.auth.dto.RefreshTokenRequest;
import org.springframework.security.oauth2.jwt.Jwt;

public interface AuthService {
    AuthResponse login(LoginRequest request);
    AuthResponse refresh(RefreshTokenRequest request);

    BasedMessage changePassword (Jwt jwt, ChangePasswordRequest request);
}
