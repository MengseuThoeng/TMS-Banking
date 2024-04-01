package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.domain.Role;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.*;
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
        request.roles().forEach(r->{
            Role newRole = roleRepository.findByName(r.name()).orElseThrow(()->
                    new ResponseStatusException(
                            HttpStatus.NOT_FOUND,"ROLE NOT FOUND"
                    ));
            roles.add(newRole);
        });
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
    public UserEditProfileResponse editProfile(String uuid, UserEditProfileRequest request) {
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

        return new UserEditProfileResponse(
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

    @Override
    public UserResponse updateByUuid(String uuid, UserUpadateRequest request) {
        User user = userRepository.findUserByUuid(uuid).orElseThrow(()->
                 new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Don't Found User you have been search it please try again!!"
        ));
        userMapping.fromUserUpadateRequest(request,user);
        user = userRepository.save(user);
        return userMapping.toUserResponse(user);
    }

    @Override
    public UserResponse findUserByUuid(String uuid) {
        User user = userRepository.findUserByUuid(uuid).orElseThrow(
                ()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,"Don't Found User"
                )
        );
        return new UserResponse(user.getUuid(),user.getName(),user.getProfileImage(),user.getGender(),user.getDob());
    }

    @Transactional
    @Override
    public void deleteUserFromDatabase(String uuid) {
        if(!userRepository.existsByUuid(uuid)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,"Not Found"
            );
        }
        userRepository.deleteByUuid(uuid);
    }

    @Transactional
    @Override
    public BasedMessage disableDeletedByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User has not been found!");
        }

        userRepository.disableDeletedByUuid(uuid);
        return new BasedMessage("you has been disable isDeleted!!");
    }
    @Transactional
    @Override
    public BasedMessage enableDeletedByUuid(String uuid) {
        if (!userRepository.existsByUuid(uuid)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "User has not been found!");
        }
        userRepository.enableDeletedByUuid(uuid);
        return new BasedMessage("you has been enable isDeleted!!");
    }
}
