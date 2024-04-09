package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {
    TransactionResponse transfers (TransactionCreateRequest request);
    Page<TransactionResponse> transactionHistory (int page, int size,String sortDirection,String transactionType);
}
