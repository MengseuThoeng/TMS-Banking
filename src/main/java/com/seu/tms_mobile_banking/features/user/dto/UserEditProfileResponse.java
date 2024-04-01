package com.seu.tms_mobile_banking.features.user.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UserEditProfileResponse(
        String uuid,
        String nationalCardId,
        String phoneNumber,
        String name,
        String profileImage,
        String gender,
        LocalDate dob,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String position,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange
) {
}
