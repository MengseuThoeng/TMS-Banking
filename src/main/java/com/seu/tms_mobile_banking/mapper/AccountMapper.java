package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.domain.UserAccount;
import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import com.seu.tms_mobile_banking.features.account.dto.AccountSnippetResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserMapping.class)
//@Mapper(componentModel = "spring", uses = {
//      UserMapping.class
//      AccountTypeMapper.class
// })
public interface AccountMapper {
    Account fromAccountCreateRequest(AccountCreateRequest request);
    @Mapping(source = "userAccountList",target = "user",qualifiedByName = "mapUserResponse")
    AccountResponse toAccountResponse(Account account);
    AccountSnippetResponse toAccountSnippetResponse (Account account);
}
