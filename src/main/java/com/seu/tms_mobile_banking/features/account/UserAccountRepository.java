package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.domain.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {
}
