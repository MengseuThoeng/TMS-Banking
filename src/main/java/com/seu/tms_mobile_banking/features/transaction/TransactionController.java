package com.seu.tms_mobile_banking.features.transaction;

import com.seu.tms_mobile_banking.features.transaction.dto.TransactionCreateRequest;
import com.seu.tms_mobile_banking.features.transaction.dto.TransactionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping
    TransactionResponse transfer (@Valid @RequestBody TransactionCreateRequest request){
        return transactionService.transfers(request);
    }
    @GetMapping
    Page<TransactionResponse> transactionHistory (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(defaultValue = "desc") String sort,
            @RequestParam(required = false) String transactionType
    ) {
        return transactionService.transactionHistory(page,size,sort,transactionType);
    }

}
