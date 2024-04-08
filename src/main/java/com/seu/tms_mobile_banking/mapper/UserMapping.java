package com.seu.tms_mobile_banking.mapper;

import com.seu.tms_mobile_banking.domain.AccountType;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.domain.UserAccount;
import com.seu.tms_mobile_banking.features.accountType.dto.AccountTypeResponse;
import com.seu.tms_mobile_banking.features.user.dto.*;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapping {
    User fromUserCreateRequest (UserCreateRequest request);

//    UserDetailResponse toUserDetailsResponse(User user);

    User fromUserEditProfileRequest(UserEditProfileRequest request);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void fromUserUpadateRequest(UserUpadateRequest request, @MappingTarget User user);

    UserDetailResponse toUserResponse(User user);
    UserResponse toUserRes(User user);

//    List<UserResponse> toUserResponeList(List<User> findAll);

    UserResponse toUserResponseList(User user);
    @Named("mapUserResponse")
    default UserResponse mapUserResponse(List<UserAccount> userAccountList){
        return toUserRes(userAccountList.get(0).getUser());
    }



}
