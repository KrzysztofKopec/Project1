package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.User;
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
    public List<UserResponse> findAll(){
        return userRepository.findAll().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }


    @PostMapping("/addUser")
    public UserCreateRequest addUser(@RequestBody UserCreateRequest userCreateRequest) {
        User user = new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName());
        userRepository.save(user);
        return userCreateRequest;
    }


    @GetMapping("/users/{userId}")
    public UserResponse getUser(@PathVariable long userId){
        return convertToUserResponse(userRepository.findById(userId));
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable long userId){
        userRepository.deleteById(userId);
        return "Delete User Id: "+ userId;
    }

    private UserResponse convertToUserResponse(User user){
        UserResponse userResponse = new UserResponse(user.getId(),user.getFirstName(),user.getLastName());
        return userResponse;
    }
}
