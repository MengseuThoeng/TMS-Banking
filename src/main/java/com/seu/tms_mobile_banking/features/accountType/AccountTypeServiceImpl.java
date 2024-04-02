package com.seu.tms_mobile_banking.features.accountType;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import com.seu.tms_mobile_banking.mapper.AccountTypeMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class AccountTypeServiceImpl implements AccountTypeService{
    private final AccountTypeRepository accountTypeRepository;
    private final AccountTypeMapping accountTypeMapping;
    @Override
    public List<AccountTypeResponse> findAllAccountType() {
        List<AccountType> accountTypes = accountTypeRepository.findAll();
        return accountTypeMapping.toAccountTypeResponseList(accountTypes);
    }

    @Override
    public AccountTypeResponse findActByAlias(String alias) {
        AccountType accountType = accountTypeRepository.findByAliasContainsIgnoreCase(alias);
        if (accountType==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Don't Found"
            );
        }
        return accountTypeMapping.toAccountTypeResponse(accountType);
    }

}
