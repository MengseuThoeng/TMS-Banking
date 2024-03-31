package com.seu.tms_mobile_banking.features.cardType;

import com.seu.tms_mobile_banking.domain.CardType;
import com.seu.tms_mobile_banking.features.cardType.dto.CardTypeResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTypeRepository extends JpaRepository<CardType,Integer> {
    CardType findByNameIgnoreCase (String name);
}
