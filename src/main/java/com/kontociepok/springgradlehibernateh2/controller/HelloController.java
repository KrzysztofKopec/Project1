package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import com.kontociepok.springgradlehibernateh2.service.CourseService;
import com.kontociepok.springgradlehibernateh2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserService userService;
    private CourseService courseService;

    @Autowired
    public HelloController(UserRepository userRepository, CourseRepository courseRepository,
                           UserService userService, CourseService courseService) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @GetMapping("/users")
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    @GetMapping("/courses")
    public List<CourseResponse> findAllCourses() {
        return courseRepository.findAll().stream().map(this::convertToCourseResponse).collect(Collectors.toList());
    }

    @PostMapping("/users")
    public UserResponse addUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName());
        return convertToUserResponse(userRepository.save(user));
    }

    @PostMapping("/courses")
    public CourseResponse addCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        Course course = new Course(courseCreateRequest.getName(), courseCreateRequest.getDescription());
        return convertToCourseResponse(courseRepository.save(course));
    }

    @GetMapping("/user/{userId}")
    public UserResponse getUser(@PathVariable long userId) {
        return convertToUserResponse(userRepository.findById(userId));
    }

    @GetMapping("/course/{courseId}")
    public CourseResponse getCourse(@PathVariable long courseId) {
        return convertToCourseResponse(courseRepository.findById(courseId));
    }

    @GetMapping("/user/{userId}/{courseId}")
    public UserResponse addCourseToUser(@PathVariable long userId, @PathVariable long courseId) {
        return convertToUserResponse(userService.addCourseToUser(userId, courseId));
    }
    @GetMapping("/course/{courseId}/{userId}")
    public CourseResponse addUserToCourse(@PathVariable long courseId, @PathVariable long userId) {
        return convertToCourseResponse(courseService.addUserToCourse(courseId, userId));
    }

    @DeleteMapping("/users/{userId}")
    public String deleteUser(@PathVariable long userId) {
        userService.deleteUserAndDeleteUserFromCourse(userId);
        return "Delete User Id: " + userId;
    }

    @DeleteMapping("/courses/{courseId}")
    public String deleteCourse(@PathVariable long courseId) {
        courseService.deleteCourseAndDeleteCourseFromUsers(courseId);
        return "Delete Course Id: " + courseId;
    }

    private UserResponse convertToUserResponse(User user) {
        UserResponse userResponse = new UserResponse(user.getId(), user.getFirstName(), user.getLastName(),
                user.getCourses().stream().map(Course::getName).collect(Collectors.toList()));
        return userResponse;
    }

    private CourseResponse convertToCourseResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse(course.getId(), course.getName(),
                course.getDescription(),
                course.getStudents().stream().map(User -> User.getFirstName()+" "+User.getLastName())
                .collect(Collectors.toList()));
        return courseResponse;
    }
}
