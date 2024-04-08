package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.domain.UserAccount;
import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountLimitTransRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountRenameRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import com.seu.tms_mobile_banking.features.accountType.AccountTypeRepository;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import com.seu.tms_mobile_banking.features.user.UserRepository;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserResponse;
import com.seu.tms_mobile_banking.mapper.AccountMapper;
import com.seu.tms_mobile_banking.mapper.AccountTypeMapper;
import com.seu.tms_mobile_banking.mapper.UserMapping;
import com.seu.tms_mobile_banking.util.RandomUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.dialect.function.PostgreSQLTruncRoundFunction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {
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
        if (accountType == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Invalid ACT"
            );
        }
        User user = userRepository.findByUuid(request.userUuid());
        if (user == null) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "User not found!!"
            );
        }
        Account account = accountMapper.fromAccountCreateRequest(request);
        account.setAccountType(accountType);
        account.setActName(user.getName());
        account.setActNo(RandomUtil.generateNineDigits());
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

//    @Override
//    public AccountResponse findAccByActNo(String actNo) {
//        Account account = accountRepository.findByActNo(actNo).orElseThrow(() ->
//                new ResponseStatusException(HttpStatus.NOT_FOUND, "Account with ActNo not found. Please try again."));
//        User user = account.getUserAccountList().stream().findFirst()
//                .map(UserAccount::getUser)
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User associated with Account not found."));
//        AccountTypeResponse accountTypeResponse = accountTypeMapper.toAccountTypeResponse(account.getAccountType());
//        AccountResponse accountResponse = accountMapper.toAccountResponse(account);
//        UserResponse userResponse = userMapping.toUserRes(user);
//        accountResponse = new AccountResponse(
//                accountResponse.actNo(),
//                accountResponse.actName(),
//                accountResponse.alias(),
//                accountResponse.balance(),
//                accountTypeResponse,
//                userResponse
//        );
//        return accountResponse;
//    }
    @Override
    public AccountResponse findAccByActNo(String actNo) {
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found"));
        return accountMapper.toAccountResponse(account);
    }

    @Override
    public AccountResponse renameByActNo(String actNo, AccountRenameRequest request) {
        //check actNo exits or not
        Account account = accountRepository.findByActNo(actNo)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Account not found ah bek"));
        // old alias and new alias
        if (account.getAlias() != null && account.getAlias().equals(request.newName())){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,"The new name cannot be the same as the old name"
            );
        }
        account.setAlias(request.newName());
        account = accountRepository.save(account);
        return accountMapper.toAccountResponse(account);
    }

    @Transactional
    @Override
    public void hideAccount(String actNo) {
        if (!accountRepository.existsByActNo(actNo)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"X Don't Found X"
            );
        }
        try {
            accountRepository.hideAccountByActNo(actNo);
        }catch (Exception ex){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,ex.getMessage());
        }
    }
    @Override
    public Page<AccountResponse> findList(int page, int size) {
        //validate page and size
        if (page<0){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,">= 0 "
            );
        }
        if (size<1){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,">= 1 Size "
            );
        }
        Sort sortByActName = Sort.by(Sort.Direction.ASC,"actName");
        PageRequest pageRequest = PageRequest.of(page,size,sortByActName);
        Page<Account> accounts = accountRepository.findAll(pageRequest);

        return accounts.map(accountMapper::toAccountResponse);
    }

    @Transactional
    @Override
    public void updateLimitTransByActNo(String actNo, AccountLimitTransRequest request) {
        if (!accountRepository.existsByActNo(actNo)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Don't Found!!!"
            );
        }
        accountRepository.updateLimitTransByActNo(request.transferLimit(),actNo);
    }
}
