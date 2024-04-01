package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.features.user.dto.*;
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
    UserEditProfileResponse editProfile  (@PathVariable String uuid, @Valid @RequestBody UserEditProfileRequest request){
        return userService.editProfile(uuid,request);
    }

    @PatchMapping("/{uuid}")
    UserResponse updateByUuid(@PathVariable String uuid,@RequestBody UserUpadateRequest request){
        return userService.updateByUuid(uuid,request);
    }
    @GetMapping("/{uuid}")
    UserResponse findByUuid(@PathVariable String uuid){
        return userService.findUserByUuid(uuid);
    }
    // Delete user by uuid hard delete
    // disable change status isDelete to true
    // enable change status isDelete to false

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

}
 