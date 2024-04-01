package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.*;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapping {
    User fromUserCreateRequest (UserCreateRequest request);

    UserDetailResponse toUserDetailsResponse(User user);

    User fromUserEditProfileRequest(UserEditProfileRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpadateRequest(UserUpadateRequest request, @MappingTarget User user);

    UserResponse toUserResponse(User user);
}
