package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountLimitTransRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountRenameRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface AccountService {
    void createNew(AccountCreateRequest request);
    AccountResponse findAccByActNo(String actNo);
    AccountResponse renameByActNo(String actNo, AccountRenameRequest request);
    void hideAccount(String actNo);

    Page<AccountResponse> findList(int page, int size);

    void updateLimitTransByActNo(String actNo, AccountLimitTransRequest request);
}
