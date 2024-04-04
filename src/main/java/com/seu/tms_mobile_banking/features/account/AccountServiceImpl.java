package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.domain.UserAccount;
import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import com.seu.tms_mobile_banking.features.accountType.AccountTypeRepository;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import com.seu.tms_mobile_banking.features.user.UserRepository;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserResponse;
import com.seu.tms_mobile_banking.mapper.AccountMapper;
import com.seu.tms_mobile_banking.mapper.AccountTypeMapper;
import com.seu.tms_mobile_banking.mapper.UserMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService{
    private final UserAccountRepository userAccountRepository;
    private final AccountMapper accountMapper;
    private final AccountRepository accountRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final AccountTypeMapper accountTypeMapper;
    @Override
    public void createNew(AccountCreateRequest request) {
        AccountType accountType = accountTypeRepository.findByAliasContainsIgnoreCase(request.accountTypeAlias());
        if (accountType==null){
            throw  new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Invalid ACT"
            );
        }
        User user = userRepository.findByUuid(request.userUuid());
        if (user==null){
            throw  new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"User not found!!"
            );
        }
        Account account = accountMapper.fromAccountCreateRequest(request);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo("987654321");
        account.setTransferLimit(BigDecimal.valueOf(5000));
        account.setIsHidden(false);
        // map account dto to account entity
        UserAccount userAccount = new UserAccount();
        userAccount.setAccount(account);
        userAccount.setUser(user);
        userAccount.setIsDeleted(false);
        userAccount.setIsBlocked(false);
        userAccount.setCreatedAt(LocalDateTime.now());
        userAccountRepository.save(userAccount);
    }

    @Override
    public AccountResponse findAccByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with ActNo not found. Please try again."));

        // Fetching the first user associated with the account, assuming one account can only be associated with one user
        User user = account.getUserAccountList().stream().findFirst()
                .map(UserAccount::getUser)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User associated with Account not found."));

        // Fetching account type information
        AccountTypeResponse accountTypeResponse = accountTypeMapper.toAccountTypeResponse(account.getAccountType());

        // Mapping the account information to AccountResponse
        AccountResponse accountResponse = accountMapper.toAccountResponse(account);

        // Mapping the user information to UserResponse
        UserResponse userResponse = userMapping.toUserRes(user);

        // Creating a new AccountResponse with the updated UserResponse and account type information
        accountResponse = new AccountResponse(
                accountResponse.actNo(),
                accountResponse.actName(),
                accountResponse.alias(),
                accountResponse.balance(),
                accountTypeResponse,
                userResponse
        );

        return accountResponse;
    }


}
