package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.features.user.dto.*;
import org.springframework.data.domain.Page;

public interface UserService {
    void createUser(UserCreateRequest request);
    void editPwd(String uuid,UserEditPasswordRequest request);
    UserEditProfileResponse editProfile (String uuid, UserEditProfileRequest request);
    UserDetailResponse updateByUuid(String uuid,UserUpadateRequest request);

    UserDetailResponse findUserByUuid(String uuid);
    void deleteUserFromDatabase(String uuid);

    BasedMessage disableDeletedByUuid(String uuid);
    BasedMessage enableDeletedByUuid(String uuid);

    Page<UserResponse> findAll(int page, int limit);

    String updateProfileImage(String mediaName,String uuid);
}
