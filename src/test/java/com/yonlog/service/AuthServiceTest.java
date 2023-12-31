package com.yonlog.service;

import com.yonlog.domain.User;
import com.yonlog.exception.AlreadyExistsEmailException;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Signup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @AfterEach
    void clean() {
        userRepository.deleteAll();
    }

    @DisplayName("회원가입 성공")
    @Test
    void signupTest() {
        // given
        Signup signup = Signup.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .name("yong")
                .build();

        // when
        authService.signup(signup);

        // then
        assertThat(userRepository.count()).isEqualTo(1);

        User user = userRepository.findAll().iterator().next();
        assertThat(user.getEmail()).isEqualTo("yongje@gmail.com");
        assertThat(user.getPassword()).isNotBlank();
        assertThat(user.getPassword()).isNotEqualTo("1234");
        assertThat(user.getName()).isEqualTo("yong");
    }

    @DisplayName("회원가입시 중복된 이메일")
    @Test
    void signupAlreadyExistsEmailTest() {
        // given
        User user = User.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .name("용제")
                .build();
        userRepository.save(user);

        Signup signup = Signup.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .name("용제")
                .build();

        // excepted
        assertThatThrownBy(() -> authService.signup(signup))
                .isInstanceOf(AlreadyExistsEmailException.class)
                .hasMessage("이미 가입된 이메일입니다.");
    }

}