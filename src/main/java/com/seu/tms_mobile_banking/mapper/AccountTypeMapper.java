package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountTypeMapper {
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);
}
