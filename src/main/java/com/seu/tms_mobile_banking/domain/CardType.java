package com.seu.tms_mobile_banking.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
@Table(name="card_types")
public class CardType {
    @Id
    private Long id;

    @Column(unique = true,nullable = false)
    private String name;

    private Boolean isDeleted;

    @OneToMany(mappedBy = "cardType")
    private List<Card> cards;
}

