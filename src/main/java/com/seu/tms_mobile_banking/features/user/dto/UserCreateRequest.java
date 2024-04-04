package com.seu.tms_mobile_banking.features.user.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.List;

public record UserCreateRequest(
        @NotNull(message = "Pin is required")
        @Max(9999)
        Integer pin,
        @NotBlank(message = "PhoneNumber is required")
        @Size(max = 20)
        String phoneNumber,
        @NotBlank
        String password,
        @NotBlank
        String confirmedPassword,
        @NotBlank
        @Size(max = 40)
        String name,
        @NotBlank
        @Size(max = 6)
        String gender,
        @NotNull
        LocalDate dob,
        @NotBlank
        @Size(max = 20)
        String nationalCardId,
        @Size(max = 20)
        String studentIdCard,
        @NotEmpty
        List<RoleRequest> roles

) {
}
