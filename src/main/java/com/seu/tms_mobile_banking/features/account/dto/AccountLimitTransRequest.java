package com.seu.tms_mobile_banking.features.account.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AccountLimitTransRequest(
        @Positive
        @NotNull
        BigDecimal transferLimit
) {
}
