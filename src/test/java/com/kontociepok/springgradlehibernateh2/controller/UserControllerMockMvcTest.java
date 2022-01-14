package com.kontociepok.springgradlehibernateh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
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
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(HelloController.class)
public class UserControllerMockMvcTest {

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
    void shouldReturnUsersWhenExist() throws Exception{
        //given
        User user = new User("Tomek","Krzysztof");
        User user1 = new User("Daniel","Bartek");
        List<User> users = List.of(user,user1);
        given(userRepository.findAll()).willReturn(users);

        //when
        ResultActions result = mockMvc.perform(get("/users").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].firstName").value("Tomek"))
                .andExpect(jsonPath("$[1].firstName").value("Daniel"));
    }
    @Test
    void shouldReturnUserByIdWhenExist() throws Exception{
        //given
        User user = new User("Tomek","Krzysztof");
        user.setId(1L);
        given(userRepository.findById(1L)).willReturn(user);

        //when
        ResultActions result = mockMvc.perform(get("/user/1").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Tomek"));
    }
    @Test
    void shouldReturnSaveUser() throws Exception{
        //given
        User user = new User("Daniel","Tomasz");
        when(userRepository.save(new User("Daniel","Tomasz"))).thenReturn(user);

        //when
        ResultActions result = mockMvc.perform(post("/users")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new User("Daniel","Tomasz"))));

        //then
        result.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName").value("Daniel"))
                .andExpect(jsonPath("$.lastName").value("Tomasz"));

    }
    @Test
    void shouldDeleteUserWhenExist() throws Exception{

        //given
        User user = new User("Daniel","Tomasz");
        given(userRepository.deleteById(1L)).willReturn("Delete User Id: 1");

        //when
        ResultActions result = mockMvc.perform(delete("/users/1"));

        //then
        assertEquals(0, userRepository.findAll().size());
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Delete User Id: 1")));

    }

}
