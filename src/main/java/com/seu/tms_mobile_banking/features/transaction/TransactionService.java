package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;

public interface TransactionService {
    TransactionResponse transfers (TransactionCreateRequest request);
}
