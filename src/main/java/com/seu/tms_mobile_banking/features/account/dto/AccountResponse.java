package com.seu.tms_mobile_banking.features.account.dto;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserResponse;

public record AccountResponse(
        String actNo,
        String actName,
        String alias,
        String balance,
        AccountTypeResponse accountTypeResponse,
        UserResponse user
) {
}
