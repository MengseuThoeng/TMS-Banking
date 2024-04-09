package com.seu.tms_mobile_banking.features.transaction.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record TransactionCreateRequest (
        @NotBlank(message = "ACCOUNT NUMBER IS REQUIRED")
        String ownerActNo,
        @NotBlank(message = "TRANSFER TO ACCOUNT NUMBER IS REQUIRED")
        String transferReceiverActNo,
        @NotNull(message = "AMOUNT IS REQUIRED")
        @Positive
        BigDecimal amount,
        String remark
){
}
