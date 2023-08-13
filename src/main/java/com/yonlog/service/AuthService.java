package com.yonlog.service;

import com.yonlog.crypto.PasswordEncoder;
import com.yonlog.domain.User;
import com.yonlog.exception.AlreadyExistsEmailException;
import com.yonlog.exception.InvalidSigninInformation;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Login;
import com.yonlog.request.Signup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signin(Login login) {
        User user = userRepository.findByEmail(login.getEmail())
                .orElseThrow(InvalidSigninInformation::new);

        boolean matches = passwordEncoder.matches(login.getPassword(), user.getPassword());
        if (!matches) {
            throw new InvalidSigninInformation();
        }

        return user.getId();
    }

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
