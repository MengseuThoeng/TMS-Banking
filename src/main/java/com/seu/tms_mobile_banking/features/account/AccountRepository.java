package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByActNo(String actNo);
}
