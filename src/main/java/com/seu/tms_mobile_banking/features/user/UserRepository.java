package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Boolean existsByPhoneNumber (String phoneNumber);
    Boolean existsByNationalCardId (String nationalCardId);
    Boolean existsByStudentIdCard (String studentIdCard);

    User findByUuid(String uuid);

//    Optional<User> findUserByUuid(String uuid);
//    @Query(value = "SELECT ",nativeQuery = true)
    @Query("SELECT u from User As u where u.uuid=:uuid")
    Optional<User> findUserByUuid(String uuid);


    void deleteByUuid (String uuid);
    Boolean existsByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = TRUE WHERE u.uuid = ?1")
    void disableDeletedByUuid(String uuid);

    @Modifying
    @Query("UPDATE User AS u SET u.isDeleted = FALSE WHERE u.uuid = ?1")
    void enableDeletedByUuid(String uuid);

}

