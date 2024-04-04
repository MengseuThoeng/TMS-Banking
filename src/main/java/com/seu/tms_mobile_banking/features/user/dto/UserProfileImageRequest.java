package com.seu.tms_mobile_banking.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserProfileImageRequest(
        @NotBlank(message = "profile image required")
        String mediaName
) {
}
