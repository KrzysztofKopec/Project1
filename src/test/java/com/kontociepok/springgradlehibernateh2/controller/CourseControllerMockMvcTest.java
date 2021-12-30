package com.kontociepok.springgradlehibernateh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import com.kontociepok.springgradlehibernateh2.service.CourseService;
import com.kontociepok.springgradlehibernateh2.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
public class CourseControllerMockMvcTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    HelloController helloController;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private CourseService courseService;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnCourseWhenExist() throws Exception{
        //given
        Course course = new Course("Biologia","xxx");
        Course course1 = new Course("Informatyka","XYZ");
        List<Course> courses = List.of(course, course1);
        given(courseRepository.findAll()).willReturn(courses);

        //when
        ResultActions result = mockMvc.perform(get("/courses").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value("Biologia"))
                .andExpect(jsonPath("$[1].name").value("Informatyka"));
    }
    @Test
    void shouldReturnCourseByIdWhenExist() throws Exception{
        //given
        Course course = new Course(1L,"Biologia","xxx");
        given(courseRepository.findById(1L)).willReturn(course);

        //when
        ResultActions result = mockMvc.perform(get("/course/1").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Biologia"));
    }
    @Test
    void shouldReturnSaveCourse() throws Exception{
        //given
        Course course = new Course("Biologia","xxx");
        when(courseRepository.save(new Course("Biologia","xxx"))).thenReturn(course);

        //when
        ResultActions result = mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(new Course("Biologia","xxx"))));

        //then
        result.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.name").value("Biologia"))
                .andExpect(jsonPath("$.description").value("xxx"));

    }

    @Test
    void shouldDeleteCourseWhenExist() throws Exception{

        //given
        Course course = new Course(1L,"Biologia","xxx");
        given(courseRepository.deleteById(1L)).willReturn("Delete Course Id: 1");

        //when
        ResultActions result = mockMvc.perform(delete("/courses/1"));

        //then
        assertEquals(0, courseRepository.findAll().size());
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Delete Course Id: 1")));

    }
}
