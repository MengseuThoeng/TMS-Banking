package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false,length = 9)
    private String actNo;

    @Column(unique = true,nullable = false,length = 100)
    private String actName;

    private BigDecimal transferLimit;

    @ManyToOne
    private AccountType accountType;

    @OneToMany(mappedBy = "account")
    private List<UserAccount> userAccountList;

    @OneToOne(mappedBy = "account")
    private Card card;
}
