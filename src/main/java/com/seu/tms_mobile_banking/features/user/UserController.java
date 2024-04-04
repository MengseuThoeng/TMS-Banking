package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.base.BasedResponse;
import com.seu.tms_mobile_banking.features.user.dto.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @GetMapping
    Page<UserResponse> findList(@RequestParam(required = false, defaultValue = "0") int page,
                                @RequestParam(required = false, defaultValue = "10") int limit) {
        return userService.findAll(page, limit);
    }


    @PutMapping("/editPwd/{uuid}")
    void editPwd(@Valid @RequestBody UserEditPasswordRequest request, @PathVariable String uuid) {
        userService.editPwd(uuid, request);
    }

    @PutMapping("/editProfile/{uuid}")
    UserEditProfileResponse editProfile  (@PathVariable String uuid, @Valid @RequestBody UserEditProfileRequest request){
        return userService.editProfile(uuid,request);
    }

    @PatchMapping("/{uuid}")
    UserDetailResponse updateByUuid(@PathVariable String uuid,@RequestBody UserUpadateRequest request){
        return userService.updateByUuid(uuid,request);
    }
    @GetMapping("/{uuid}")
    UserDetailResponse findByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("{uuid}")
    void deleteUserFromDatabase (@PathVariable String uuid){
        userService.deleteUserFromDatabase(uuid);
    }
    @PutMapping("/{uuid}/disable")
    BasedMessage disableDeletedByUuid(@PathVariable String uuid){
        return userService.disableDeletedByUuid(uuid);
    }
    @PutMapping("/{uuid}/enable")
    BasedMessage enableDeletedByUuid(@PathVariable String uuid){
        return userService.enableDeletedByUuid(uuid);
    }

    @PutMapping("/{uuid}/profile-image")
    BasedResponse<?> updateProfileImage (@PathVariable String uuid, @RequestBody UserProfileImageRequest request){
        String newProfileImageUri = userService.updateProfileImage(request.mediaName(),uuid);
        return BasedResponse.builder().payload(newProfileImageUri).build();
    }
}