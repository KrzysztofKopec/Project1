package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class UserIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;
    @Test
    void shouldReturnAllUsers() throws Exception {
        // given
        userRepository.clear();
        userRepository.save(new User(1L, "Bartek"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly(new User(1L, "Bartek"));
    }
    @Test
    void shouldReturnUserById() throws Exception {
        // given
        userRepository.clear();
        userRepository.save(new User(1L, "banan"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users/1", User.class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new User(1L, "banan"));
    }
    @Test
    void shouldSaveUser() throws Exception {
        // given
        userRepository.clear();

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/addUser",
                new User(2L,"Bartek"),User.class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new User(2L, "Bartek"));
    }
    @Test
    void shouldDeleteUserById() throws Exception {
        // given
        userRepository.clear();
        userRepository.save(new User(1L, "Krzysztof"));
        userRepository.save(new User(2L, "Bartek"));

        // when
        restTemplate.delete("http://localhost:" + port + "/users/1", User.class);
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", User[].class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);

    }

}
