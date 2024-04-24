package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
