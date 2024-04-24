package com.seu.tms_mobile_banking.init;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.domain.Authority;
import com.seu.tms_mobile_banking.domain.CardType;
import com.seu.tms_mobile_banking.domain.Role;
import com.seu.tms_mobile_banking.features.accountType.AccountTypeRepository;
import com.seu.tms_mobile_banking.features.cardType.CardTypeRepository;
import com.seu.tms_mobile_banking.features.user.AuthorityRepository;
import com.seu.tms_mobile_banking.features.user.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInit {

    private final RoleRepository roleRepository;
    private final CardTypeRepository cardTypeRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final AuthorityRepository authorityRepository;

    @PostConstruct
    void init() {
        Authority userRead = new Authority();
        userRead.setName("user:read");
        Authority userWrite = new Authority();
        userWrite.setName("user:write");
        Authority transactionRead = new Authority();
        transactionRead.setName("transaction:read");
        Authority transactionWrite = new Authority();
        transactionWrite.setName("transaction:write");
        Authority accountRead = new Authority();
        accountRead.setName("account:read");
        Authority accountWrite = new Authority();
        accountWrite.setName("account:write");
        Authority accountTypeRead = new Authority();
        accountTypeRead.setName("accountType:read");
        Authority accountTypeWrite = new Authority();
        accountTypeWrite.setName("accountType:write");

        Role user = new Role();
        user.setName("USER");
        user.setAuthorities(List.of(
                userRead, transactionRead,
                accountRead, accountTypeRead
        ));

        Role customer = new Role();
        customer.setName("CUSTOMER");
        customer.setAuthorities(List.of(
                userWrite, transactionWrite,
                accountWrite
        ));

        Role staff = new Role();
        staff.setName("STAFF");
        staff.setAuthorities(List.of(
                accountTypeWrite
        ));

        Role admin = new Role();
        admin.setName("ADMIN");
        admin.setAuthorities(List.of(
                userWrite, accountWrite,
                accountTypeWrite
        ));

        roleRepository.saveAll(
                List.of(user, customer, staff, admin)
        );
    }


    @PostConstruct
    void accountTypeInit() {
        if (accountTypeRepository.count() < 1) {
            AccountType saving = new AccountType();
            saving.setName("Saving Account");
            saving.setAlias("Saving");
            saving.setIsDeleted(false);
            saving.setDescription("An account designed for saving money.");
            AccountType payroll = new AccountType();
            payroll.setName("Payroll Account");
            payroll.setAlias("Payroll");
            payroll.setIsDeleted(false);
            payroll.setDescription("An account designed for payroll money.");
            accountTypeRepository.saveAll(List.of(saving, payroll));
        }
    }

    @PostConstruct
    void cardTypeInit() {
        if (cardTypeRepository.count() < 1) {
            CardType visa = new CardType();
            visa.setName("VISA");
            visa.setIsDeleted(false);
            CardType master = new CardType();
            master.setName("MASTER CARD");
            master.setIsDeleted(false);
            cardTypeRepository.saveAll(List.of(visa, master));
        }
    }
}
