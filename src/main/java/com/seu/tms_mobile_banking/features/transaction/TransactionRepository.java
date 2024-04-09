package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction,Long> {
}
