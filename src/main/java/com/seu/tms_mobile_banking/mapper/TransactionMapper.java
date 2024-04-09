package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.Transaction;
import com.seu.tms_mobile_banking.features.transaction.TransactionRepository;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = AccountMapper.class)
public interface TransactionMapper {
    TransactionResponse toTransactionResponse (Transaction transaction);
    Transaction fromTransactionCreateRequest(TransactionCreateRequest request);
}
