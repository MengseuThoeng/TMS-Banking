package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest request);
    AccountResponse toAccountResponse(Account account);
}
