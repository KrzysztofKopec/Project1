package com.kontociepok.springgradlehibernateh2.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.*;
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

    @Test
    void shouldReturnListSizeAndNameUsers() throws Exception{
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
    void shouldReturnUserById() throws Exception{
        //given
        User user = new User("Tomek","Krzysztof");

        given(userRepository.findById(1L)).willReturn(user);

        //when
        ResultActions result = mockMvc.perform(get("/users/1").contentType(MediaType.APPLICATION_JSON));

        //then
        result.andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("Tomek"));
    }
    @Test
    void shouldReturnSaveUser() throws Exception{
        User user = new User("Daniel","Tomasz");

        when(userRepository.save(new User("Daniel","Tomasz"))).thenReturn(user);

        ResultActions result = mockMvc.perform(post("/addUser")
                    .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new User("Daniel","Tomasz"))));

        result.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstName").value("DAniel"));
    }
    @Test
    void shouldDelete() throws Exception{

        given(userRepository.deleteById(1L)).willReturn("Delete User Id: 1");

        ResultActions result = mockMvc.perform(delete("/users/1"));

        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Delete User Id: 1")));

    }

}
