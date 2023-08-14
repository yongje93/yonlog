package com.yonlog.service;

import com.yonlog.crypto.PasswordEncoder;
import com.yonlog.domain.User;
import com.yonlog.exception.AlreadyExistsEmailException;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(Signup signup) {
        Optional<User> userOptional = userRepository.findByEmail(signup.getEmail());
        if (userOptional.isPresent()) {
            throw new AlreadyExistsEmailException();
        }

        String encryptedPassword = passwordEncoder.encrypt(signup.getPassword());

        User user = User.builder()
                .email(signup.getEmail())
                .password(encryptedPassword)
                .name(signup.getName())
                .build();

        userRepository.save(user);
    }
}
