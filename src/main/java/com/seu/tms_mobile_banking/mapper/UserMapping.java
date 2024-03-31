package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserEditProfileRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapping {
    User fromUserCreateRequest (UserCreateRequest request);

    UserDetailResponse toUserDetailsResponse(User user);

    User fromUserEditProfileRequest(UserEditProfileRequest request);

}
