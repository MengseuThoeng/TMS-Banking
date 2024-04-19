package com.seu.tms_mobile_banking.init;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.domain.CardType;
import com.seu.tms_mobile_banking.domain.Role;
import com.seu.tms_mobile_banking.features.accountType.AccountTypeRepository;
import com.seu.tms_mobile_banking.features.cardType.CardTypeRepository;
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
    @PostConstruct
    void init(){
        //CardType
        if (cardTypeRepository.count()<1) {
            CardType visa = new CardType();
            visa.setName("VISA");
            visa.setIsDeleted(false);
            CardType master = new CardType();
            master.setName("MASTER CARD");
            master.setIsDeleted(false);
            cardTypeRepository.saveAll(List.of(visa, master));
        }
        //Role
        if (roleRepository.count()<1) {
            Role user = new Role();
            user.setName("USER");
            Role customer = new Role();
            customer.setName("CUSTOMER");
            Role staff = new Role();
            staff.setName("STAFF");
            Role admin = new Role();
            admin.setName("ADMIN");
            roleRepository.saveAll(List.of(user,customer,admin,staff));
        }
        //AccountType
        if(accountTypeRepository.count()<1){
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
            accountTypeRepository.saveAll(List.of(saving,payroll));
        }
    }
}
