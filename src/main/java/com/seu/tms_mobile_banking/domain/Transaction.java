package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Account sender;

    @ManyToOne
    private Account receiver;

    private BigDecimal amount;
    private String remark;
    private Boolean isPayment;
    private LocalDateTime transactionAt;
    private Boolean isDeleted;

}
