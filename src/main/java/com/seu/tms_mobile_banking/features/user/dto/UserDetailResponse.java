package com.seu.tms_mobile_banking.features.user.dto;

import com.seu.tms_mobile_banking.domain.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record UserDetailResponse(
        String uuid,
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
        BigDecimal monthlyIncomeRange,
        String studentIdCard,
        List<RoleNameResponse> roles

) {
}
