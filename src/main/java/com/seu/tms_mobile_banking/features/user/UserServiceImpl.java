package com.seu.tms_mobile_banking.features.user;

import com.seu.tms_mobile_banking.domain.User;
import com.seu.tms_mobile_banking.features.user.dto.UserCreateRequest;
import com.seu.tms_mobile_banking.mapper.UserMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapping userMapping;

    @Override
    public void createUser(UserCreateRequest request) {
        User user = userMapping.fromUserCreateRequest(request);

        userRepository.save(user);
    }
}
