package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable long userId) {
        User user = userRepository.findById(userId);
        return user;
    }
    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable long userId){
        userRepository.deleteById(userId);
        return "Delete User no Id: "+ userId;
    }
}
