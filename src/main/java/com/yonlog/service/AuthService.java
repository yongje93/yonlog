package com.yonlog.service;

import com.yonlog.domain.Session;
import com.yonlog.domain.User;
import com.yonlog.exception.InvalidSigninInformation;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Login;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);
        Session session = user.addSession();

        return user.getId();
    }
}
