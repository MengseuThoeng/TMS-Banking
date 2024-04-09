package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.domain.Account;
import com.seu.tms_mobile_banking.domain.Transaction;
import com.seu.tms_mobile_banking.features.account.AccountRepository;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;
import com.seu.tms_mobile_banking.mapper.TransactionMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService{
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    @Override
    public TransactionResponse transfers(TransactionCreateRequest request) {
        log.info("transfers(TransactionCreateRequest request) :{}",request);
        // validation owner account number and receiver account number
        Account owner = accountRepository.findByActNo(request.ownerActNo())
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,"Not Found Owner!!"
                        ));
        Account receiver = accountRepository.findByActNo(request.transferReceiverActNo())
                .orElseThrow(()->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,"Not Found Receiver!!"
                        ));
        // check amount transfer
        if (owner.getBalance().doubleValue()<request.amount().doubleValue()){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"INSUFFICIENT BALANCE"
            );
        }
        // check amount transfer
        if (owner.getTransferLimit().doubleValue() < request.amount().doubleValue()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"TRANSACTION TRANSFER HAS BEEN OVER LIMIT!!"
            );
        }
        // withdraw from owner
        owner.setBalance(owner.getBalance().subtract(request.amount()));
        // deposit to receiver
        receiver.setBalance(receiver.getBalance().add(request.amount()));
        Transaction transaction = transactionMapper.fromTransactionCreateRequest(request);
        transaction.setOwner(owner);
        transaction.setTransferReceiver(receiver);
        transaction.setTransactionType("Transfer");
        transaction.setTransactionAt(LocalDateTime.now());
        transaction.setStatus(true);
        transaction = transactionRepository.save(transaction);

        return transactionMapper.toTransactionResponse(transaction);
    }
}
