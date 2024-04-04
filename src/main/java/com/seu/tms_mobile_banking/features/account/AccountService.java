package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;

public interface AccountService {
    void createNew(AccountCreateRequest request);

    AccountResponse findAccByActNo(String actNo);
}
