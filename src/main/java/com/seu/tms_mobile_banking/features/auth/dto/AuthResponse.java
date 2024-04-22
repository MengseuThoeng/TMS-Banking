package com.seu.tms_mobile_banking.features.auth.dto;

public record AuthResponse(
        String type,
        String accessToken,
        String refreshToken

) {
}
