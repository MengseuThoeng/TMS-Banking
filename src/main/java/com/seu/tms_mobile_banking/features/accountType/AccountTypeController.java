package com.seu.tms_mobile_banking.features.accountType;

import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/act")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService accountTypeService;

    @GetMapping
    List<AccountTypeResponse> findAllAccountType(){
        return accountTypeService.findAllAccountType();
    }
    @GetMapping("/{alias}")
    AccountTypeResponse findActByAlias(@PathVariable String alias){
        return accountTypeService.findActByAlias(alias);
    }

}
