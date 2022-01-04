package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
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
public class CourseIntegrationTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void shouldReturnAllCoursesWhenExist() {
        // given
        courseRepository.clear();
        courseRepository.save(new Course("Biologia","abcd"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses", CourseResponse[].class);

        // then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody().length == 1);
        assertThat(result.getBody()).containsExactly(new CourseResponse(1L,"Biologia","abcd", List.of()));
    }
    @Test
    void shouldReturnCourseByIdWhenExist() {
        // given
        courseRepository.clear();
        courseRepository.save(new Course("Biologia", "abcd"));

        // when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/course/1", CourseResponse.class);

        // then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseResponse(1L,"Biologia","abcd", List.of()));
    }
    @Test
    void shouldSaveUser() {
        // given
        courseRepository.clear();

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/courses",
                new CourseCreateRequest("Biologia","abcd"),CourseCreateRequest.class);

        // then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseCreateRequest("Biologia", "abcd"));
    }
    @Test
    void shouldDeleteUserByIdWhenExist() {
        // given
        courseRepository.clear();
        courseRepository.save(new Course("Biologia", "abcd"));
        courseRepository.save(new Course("Geografia", "dcba"));

        // when
        restTemplate.delete("http://localhost:" + port + "/courses/1", Course.class);

        // then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/courses", CourseResponse[].class);
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).hasSize(1);
    }
    @Test
    void shouldReturnErrorIsEmptyNameCourse() {
        // given
        CourseCreateRequest course = new CourseCreateRequest("","abcd");

        // when
        var result = restTemplate.postForEntity("http://localhost:" + port + "/courses",
                course, CourseCreateRequest.class);

        // then
        assertThat(result.getStatusCodeValue() == 400);
    }
    @Test
    void shouldReturnAllUsersOfCourseWhenExist(){
        //given
        courseRepository.clear();
        Course course = new Course("Fizyka", "Atom");
        course.addStudentId(1L);
        course.addStudentId(2L);
        courseRepository.save(course);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/course/1/users", List.class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(List.of("Tomek banan","Mietek orange"));
    }
    @Test
    void shouldReturnAddedUserToCourseWhenExist(){
        //given
        restTemplate.put("http://localhost:" + port + "/course/1/user/1", CourseResponse.class);

        //when
        var result = restTemplate.getForEntity("http://localhost:" + port + "/course/1", CourseResponse.class);

        //then
        assertThat(result.getStatusCodeValue() == 200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseResponse(1L,"Informatyka","klub wew.",List.of("Tomek banan")));
    }
    @Test
    void shouldReturnUpdateCourseWhenExist(){
        //given
        courseRepository.clear();
        courseRepository.save(new Course("Fizyka", "Atom"));

        //when
        restTemplate.put("http://localhost:" + port + "/course/1/update",
                new CourseCreateRequest("Informatyka","Bajtek"),CourseCreateRequest.class);

        //then
        var result = restTemplate.getForEntity("http://localhost:" + port + "/course/1", CourseResponse.class);
        assertThat(result.getStatusCodeValue()==200);
        assertThat(result.hasBody()).isTrue();
        assertThat(result.getBody()).isEqualTo(new CourseResponse(1L,"Informatyka","Bajtek", List.of()));
    }
}
