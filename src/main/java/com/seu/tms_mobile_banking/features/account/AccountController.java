package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest request){
        accountService.createNew(request);
    }
    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo) {
        return accountService.findAccByActNo(actNo);
    }

}
