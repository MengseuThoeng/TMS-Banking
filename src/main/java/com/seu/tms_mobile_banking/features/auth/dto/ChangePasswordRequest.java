package com.seu.tms_mobile_banking.features.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "Old Password is required")
        String oldPassword,
        @NotBlank(message = "New Password is required")
        String newPassword,
        @NotBlank(message = "Confirm Password is required")
        String confirmedPassword
) {
}
