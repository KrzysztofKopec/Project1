package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

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
    void shouldReturnAllUsersWhenExist() {
        // given
        userRepository.clear();
        userRepository.save(new User("Alek", "Bartek"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", UserResponse[].class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).containsExactly(new UserResponse(1L,"Alek", "Bartek", List.of()));
    }
    @Test
    void shouldReturnUserByIdWhenExist() {
        // given
        userRepository.clear();
        userRepository.save(new User("Alek", "banan"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/user/1", UserResponse.class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new UserResponse(1L,"Alek", "banan",List.of()));
    }
    @Test
    void shouldSaveUser() {
        // given
        userRepository.clear();

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/users",
                new UserCreateRequest("Alek","Bartek"),UserCreateRequest.class);

        // then
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new UserCreateRequest("Alek", "Bartek"));
    }
    @Test
    void shouldDeleteUserByIdWhenExist() {
        // given
        userRepository.clear();
        userRepository.save(new User("Alek", "Krzysztof"));
        userRepository.save(new User("Tomek", "Bartek"));

        // when
        restTemplate.delete("http://localhost:" + port + "/users/1", User.class);

        // then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/users", UserResponse[].class);
        assertThat(result.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);

    }
    @Test
    void shouldReturnErrorIsEmptyFirstNameUser() {
        // given
        UserCreateRequest user = new UserCreateRequest("","Bartek");

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/users",
                user, UserCreateRequest.class);

        // then
        assertThat(result.getStatusCodeValue() == 400);
    }
    @Test
    void shouldReturnAllCoursesOfUserWhenExist(){
       //given
        User user = new User("Alek", "Bartek");
        user.addCourseId(1L);
        user.addCourseId(2L);
        userRepository.save(user);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/user/3/courses", List.class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(List.of("Informatyka","Matematyka"));
    }
    @Test
    void shouldReturnAddedCourseToUserWhenExist(){
        //given
        restTemplate.put("http://localhost:" + port + "/user/1/course/1", UserResponse.class);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/user/1", UserResponse.class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new UserResponse(1L,"Tomek", "banan",List.of("informatyka")));
    }
}
