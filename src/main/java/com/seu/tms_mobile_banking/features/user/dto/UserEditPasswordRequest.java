package com.seu.tms_mobile_banking.features.user.dto;

import jakarta.validation.constraints.NotBlank;

public record UserEditPasswordRequest(
        @NotBlank
        String oldPwd,
        @NotBlank
        String newPwd,
        @NotBlank
        String ConfirmedPwd
) {
}
