package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;


    @Test
    void shouldSaveNewUserAndReturnListSize(){
        //given
        userRepository.clear();
        userRepository.save(new User("Bal","Krzysztof"));

        //when
        var result = userRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(1);
    }
    @Test
    void shouldReturnUserById(){
        //given
        userRepository.save(new User("Tolek","banan"));
        userRepository.save(new User("Mietek","orange"));

        //when
        var result = userRepository.findById(2L);
        //then
        assertThat(result.getFirstName()).isEqualTo("Mietek");
    }
    @Test
    void shouldReturnAllListSize(){
        //given
        userRepository.save(new User("Tolek","banan"));
        userRepository.save(new User("Mietek","orange"));
        //when
        var result = userRepository.findAll();
        //then
        assertThat(result.size()).isEqualTo(2);
    }
    @Test
    void shouldDeleteUserByIdAndReturnListSize(){
        //given
        userRepository.save(new User("Tolek","banan"));
        userRepository.save(new User("Mietek","orange"));
        userRepository.deleteById(1L);
        //when
        var all = userRepository.findAll();
        //then
        assertThat(all.size()).isEqualTo(1);

    }

}