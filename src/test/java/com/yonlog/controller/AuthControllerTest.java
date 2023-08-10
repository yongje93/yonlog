package com.yonlog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yonlog.domain.User;
import com.yonlog.repository.SessionRepository;
import com.yonlog.repository.UserRepository;
import com.yonlog.request.Login;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @BeforeEach
    void clean() {
        userRepository.deleteAll();
        sessionRepository.deleteAll();
    }

    @DisplayName("로그인 성공")
    @Test
    void loginTest() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("용제")
                .email("yongje@gmail.com")
                .password("1234")
                .build());

        userRepository.save(user);

        Login login = Login.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login))
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @DisplayName("로그인 성공후 세션 1개 생성")
    @Transactional
    @Test
    void loginAndCreateSessionTest() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("용제")
                .email("yongje@gmail.com")
                .password("1234")
                .build());

        userRepository.save(user);

        Login login = Login.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login))
                )
                .andExpect(status().isOk())
                .andDo(print());

        assertThat(user.getSessions().size()).isEqualTo(1L);
    }

    @DisplayName("로그인 성공후 세션 응답")
    @Test
    void loginAndReturnSessionTest() throws Exception {
        // given
        User user = userRepository.save(User.builder()
                .name("용제")
                .email("yongje@gmail.com")
                .password("1234")
                .build());

        userRepository.save(user);

        Login login = Login.builder()
                .email("yongje@gmail.com")
                .password("1234")
                .build();

        // expected
        mockMvc.perform(post("/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(login))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accessToken", notNullValue()))
                .andDo(print());
    }

}