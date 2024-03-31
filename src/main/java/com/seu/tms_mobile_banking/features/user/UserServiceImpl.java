package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.Role;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserDetailResponse;
import com.seu.tms_mobile_banking.features.user.dto.UserEditPasswordRequest;
import com.seu.tms_mobile_banking.features.user.dto.UserEditProfileRequest;
import com.seu.tms_mobile_banking.mapper.UserMapping;
import jakarta.transaction.Transactional;
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
    @Transactional
    @Override
    public void editPwd(String uuid,UserEditPasswordRequest request) {
        User user = userRepository.findByUuid(uuid);
        if (!request.oldPwd().equals(user.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Old Password Incorrect"
            );
        }
        if (!request.newPwd().equals(request.ConfirmedPwd())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "New Password and Confirmed Password doesn't match"
            );
        }
        user.setPassword(request.newPwd());
        userRepository.save(user);
    }

    @Transactional
    @Override
    public UserDetailResponse editProfile(String uuid, UserEditProfileRequest request) {
        User user = userRepository.findByUuid(uuid);
        if (user==null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Don't Found User you have been search it please try again!!"
            );
        }
        user.setPhoneNumber(request.phoneNumber());
        user.setCityOrProvince(request.cityOrProvince());
        user.setKhanOrDistrict(request.khanOrDistrict());
        user.setSangkatOrCommune(request.sangkatOrCommune());
        user.setEmployeeType(request.employeeType());
        user.setPosition(request.position());
        user.setCompanyName(request.companyName());
        user.setMainSourceOfIncome(request.mainSourceOfIncome());
        user.setMonthlyIncomeRange(request.monthlyIncomeRange());
        userRepository.save(user);

        return new UserDetailResponse(
                user.getUuid(),
                user.getNationalCardId(),
                user.getPhoneNumber(),
                user.getName(),
                user.getProfileImage(),
                user.getGender(),
                user.getDob(),
                user.getCityOrProvince(),
                user.getKhanOrDistrict(),
                user.getSangkatOrCommune(),
                user.getEmployeeType(),
                user.getPosition(),
                user.getCompanyName(),
                user.getMainSourceOfIncome(),
                user.getMonthlyIncomeRange()
                );
    }
}
