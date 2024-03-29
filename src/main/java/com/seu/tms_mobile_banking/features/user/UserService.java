package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;

public interface UserService {
    void createUser(UserCreateRequest request);
}
