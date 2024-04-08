package com.seu.tms_mobile_banking.features.account;

import com.seu.tms_mobile_banking.features.account.dto.AccountCreateRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountLimitTransRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountRenameRequest;
import com.seu.tms_mobile_banking.features.account.dto.AccountResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final AccountService accountService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody AccountCreateRequest request) {
        accountService.createNew(request);
    }

    @GetMapping("/{actNo}")
    AccountResponse findByActNo(@PathVariable String actNo) {
        return accountService.findAccByActNo(actNo);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping("/{actNo}/rename")
    AccountResponse renameByActNo(@PathVariable String actNo, @RequestBody AccountRenameRequest request){
        return accountService.renameByActNo(actNo,request);
    }

    @PutMapping("/{actNo}/hide")
    void hideAcc(@PathVariable String actNo){
        accountService.hideAccount(actNo);
    }
    @GetMapping
    Page<AccountResponse> findList(
            @RequestParam(required = false,defaultValue = "0") int page,
            @RequestParam(required = false,defaultValue = "15") int size
    ){
        return accountService.findList(page,size);
    }
    @PutMapping("/{actNo}/limit")
    void updateTransferLimit (@PathVariable String actNo,@RequestBody @Valid AccountLimitTransRequest request){
        accountService.updateLimitTransByActNo(actNo,request);
    }
}
