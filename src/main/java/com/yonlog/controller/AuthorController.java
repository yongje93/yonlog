package com.yonlog.controller;

import com.yonlog.domain.User;
import com.yonlog.exception.InvalidSigninInformation;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Login;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final UserRepository userRepository;

    @PostMapping("/auth/login")
    public User login(@RequestBody @Valid Login login) {
        log.info(">>> login = {}", login);

        User user = userRepository.findByEmailAndPassword(login.getEmail(), login.getPassword())
                .orElseThrow(InvalidSigninInformation::new);

        return user;

    }
}
