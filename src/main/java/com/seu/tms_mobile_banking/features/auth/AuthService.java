package com.seu.tms_mobile_banking.features.auth;

import com.seu.tms_mobile_banking.features.auth.dto.AuthResponse;
import com.seu.tms_mobile_banking.features.auth.dto.LoginRequest;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
