package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserEditPasswordRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserEditProfileRequest;

public interface UserService {
    void createUser(UserCreateRequest request);
    void editPwd(String uuid,UserEditPasswordRequest request);

    UserDetailResponse editProfile (String uuid,UserEditProfileRequest request);

}
