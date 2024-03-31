package com.seu.tms_mobile_banking.features.accountType;

import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

public interface AccountTypeService {
    List<AccountTypeResponse> findAllAccountType();
    AccountTypeResponse findActByAlias(String alias);
}
