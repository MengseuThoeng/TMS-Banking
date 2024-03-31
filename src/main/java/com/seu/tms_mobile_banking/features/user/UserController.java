package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserEditPasswordRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserEditProfileRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    void createNew(@Valid @RequestBody UserCreateRequest request){
        userService.createUser(request);
    }

    @PutMapping("/editPwd/{uuid}")
    void editPwd(@Valid @RequestBody UserEditPasswordRequest request, @PathVariable String uuid) {
        userService.editPwd(uuid, request);
    }

    @PutMapping("/editProfile/{uuid}")
    UserDetailResponse editProfile  (@PathVariable String uuid, @Valid @RequestBody UserEditProfileRequest request){
        return userService.editProfile(uuid,request);
    }
}
