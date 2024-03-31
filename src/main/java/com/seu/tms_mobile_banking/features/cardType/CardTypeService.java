package com.seu.tms_mobile_banking.features.cardType;

import com.seu.tms_mobile_banking.features.cardType.dto.CardTypeResponse;

import java.util.List;

public interface CardTypeService {
    List<CardTypeResponse> findAllCardType();
    CardTypeResponse findCardTypeByName(String name);
}
