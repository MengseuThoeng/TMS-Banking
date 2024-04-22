package com.seu.tms_mobile_banking.features.auth.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Phone number is required")
        String phoneNumber,
        @NotBlank(message = "Password is required")
        String password
) {
}
