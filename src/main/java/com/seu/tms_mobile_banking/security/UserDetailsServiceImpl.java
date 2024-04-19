package com.seu.tms_mobile_banking.security;

import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByPhoneNumber(username).orElseThrow(
                ()->new UsernameNotFoundException(
                        "User has not been found"
                )
        );
        log.info("User :"+user);
        CustomUserDetail customUserDetail = new CustomUserDetail();
        customUserDetail.setUser(user);
        return customUserDetail;
    }
}
