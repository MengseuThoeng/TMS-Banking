package com.seu.tms_mobile_banking.features.accountType.dto;

public record AccountTypeResponse(
        String name,
        String alias,
        String description,
        Boolean isDeleted
) {
}
