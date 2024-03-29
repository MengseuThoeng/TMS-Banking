package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByPhoneNumber (String phoneNumber);
    Boolean existsByNationalCardId (String nationalCardId);
    Boolean existsByStudentIdCard (String studentIdCard);
}

