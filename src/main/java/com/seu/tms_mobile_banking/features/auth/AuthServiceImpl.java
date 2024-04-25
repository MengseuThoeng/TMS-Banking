package com.seu.tms_mobile_banking.features.auth;

import com.seu.tms_mobile_banking.base.BasedMessage;
import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.auth.dto.AuthResponse;
import com.seu.tms_mobile_banking.features.auth.dto.ChangePasswordRequest;
import com.seu.tms_mobile_banking.features.auth.dto.LoginRequest;
import com.seu.tms_mobile_banking.features.auth.dto.RefreshTokenRequest;
import com.seu.tms_mobile_banking.features.token.TokenService;
import com.seu.tms_mobile_banking.features.user.UserRepository;
import com.seu.tms_mobile_banking.security.CustomUserDetail;
import com.seu.tms_mobile_banking.security.SecurityBean;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.server.ResponseStatusException;


import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse login(LoginRequest request) {
        // Auth with DTO
        Authentication auth  = new UsernamePasswordAuthenticationToken(
                request.phoneNumber(),
                request.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);
        return tokenService.createToken(auth);
    }

    @Override
    public AuthResponse refresh(RefreshTokenRequest request) {
        Authentication auth  = new BearerTokenAuthenticationToken(
                request.refreshToken()
        );
        auth = jwtAuthenticationProvider.authenticate(auth);


        return tokenService.createToken(auth);

    }

    @Override
    public BasedMessage changePassword(Jwt jwt, ChangePasswordRequest request) {


        User user = userRepository.findByPhoneNumber(jwt.getId())
                .orElseThrow(()->new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "User has not been found in our system!!"
                        )
                );
        if (!passwordEncoder.matches(request.oldPassword(),user.getPassword())){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"Old password incorrect..!"
            );
        }
        if (!request.newPassword().equals(request.confirmedPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,"New Password and Confirm password isn't the same please try again..!"
            );
        }
        user.setPassword(passwordEncoder.encode(request.newPassword()));
        userRepository.save(user);
        return new BasedMessage("Your Password has been changed successfully....!!");
    }
}
