package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> {
    Optional<Account> findByActNo(String actNo);
    @Modifying
    @Query("""
        UPDATE Account AS a
        set a.isHidden = TRUE
        WHERE a.actNo = ?1
    """)
    void hideAccountByActNo(String actNo);
    Boolean existsByActNo(String actNo);

    @Modifying
    @Query("""
        update Account as   a
        set a.transferLimit = ?1
        where a.actNo=?2
    """)
    void updateLimitTransByActNo(BigDecimal transferLimit,String actNo);
}
