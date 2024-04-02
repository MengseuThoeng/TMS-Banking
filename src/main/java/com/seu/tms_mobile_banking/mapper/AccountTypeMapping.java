package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import org.mapstruct.Mapper;
import org.springframework.context.annotation.Bean;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AccountTypeMapping {
    List<AccountTypeResponse> toAccountTypeResponseList(List<AccountType> accountTypes);
    AccountTypeResponse toAccountTypeResponse(AccountType accountType);



}
