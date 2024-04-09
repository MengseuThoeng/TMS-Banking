package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    TransactionResponse transfer (@Valid @RequestBody TransactionCreateRequest request){
        return transactionService.transfers(request);
    }

}
