package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.model.UserDTO;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private UserRepository userRepository;


    @Autowired
    public HelloController(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }


    @GetMapping("/users")
    public List<UserDTO> findAll(){
        return userRepository.findAll().stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }


    @PostMapping("/addUser")
    public UserDTO addUser(@RequestBody UserDTO userDTO) {
        User user = new User(userDTO.getId(), userDTO.getFirstName());
        userRepository.save(user);
        return userDTO;
    }


    @GetMapping("/users/{userId}")
    public UserDTO getUser(@PathVariable long userId){
        return convertToUserDTO(userRepository.findById(userId));
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable long userId){
        userRepository.deleteById(userId);
        return "Delete User Id: "+ userId;
    }

    private UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        return userDTO;
    }
}
