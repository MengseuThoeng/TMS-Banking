package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true,nullable = false)
    private String number;
    @Column(nullable = false)
    private String holder;
    private String cvv;
    private LocalDate issuedAt;
    private LocalDate expiredAt;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name="type_id")
    private CardType cardType;
    @OneToOne
    private Account account;
}
