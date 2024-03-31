package com.seu.tms_mobile_banking.features.accountType;

import com.seu.tms_mobile_banking.domain.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTypeRepository extends JpaRepository<AccountType,Integer> {
    AccountType findByAliasContainsIgnoreCase(String alias);
}
