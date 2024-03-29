package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.Role;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.mapper.UserMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;
    private final RoleRepository roleRepository;

    @Override
    public void createUser(UserCreateRequest request) {
        if (userRepository.existsByPhoneNumber((request.phoneNumber()))){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "PhoneNumber Dupplication"
            );
        }
        if (userRepository.existsByNationalCardId((request.nationalCardId()))){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "NationalCard Dupplication"
            );
        }
        if (userRepository.existsByStudentIdCard((request.studentIdCard()))){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "studentIdCard Dupplication"
            );
        }
        if(!request.password().equals(request.confirmedPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Password doesn't match"
            );
        }
        User user = userMapping.fromUserCreateRequest(request);
        user.setUuid(UUID.randomUUID().toString());

        user.setProfileImage("avatar.png");
        user.setCreatedAt(LocalDateTime.now());
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        List<Role> roles = new ArrayList<>();
        Role userRole = roleRepository.findByName("USER").orElseThrow(
                ()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"ROLE NOT FOUND"
                )
        );
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);
    }


}
