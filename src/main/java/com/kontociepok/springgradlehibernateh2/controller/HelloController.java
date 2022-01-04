package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.service.CourseService;
import com.kontociepok.springgradlehibernateh2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HelloController {

    private UserService userService;
    private CourseService courseService;

    @Autowired
    public HelloController(UserService userService, CourseService courseService) {
        this.userService = userService;
        this.courseService = courseService;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return "Hello " + name;
    }

    @GetMapping("/users")
    public List<UserResponse> findAllUsers() {
        return userService.findAll().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }
    @GetMapping("/user/{userId}/courses")
    public List<String> allCoursesOfUser(@PathVariable long userId){
        return userService.findById(userId).getCoursesId().stream().map(e -> courseService.findById(e).getName())
                .collect(Collectors.toList());
    }
    @GetMapping("/user/{userId}/courseGrades/{courseId}")
    public String courseGrades(@PathVariable long userId, @PathVariable long courseId){
        return userService.courseGrades(userId, courseId);
    }

    @GetMapping("/courses")
    public List<CourseResponse> findAllCourses() {
        return courseService.findAll().stream().map(this::convertToCourseResponse).collect(Collectors.toList());
    }
    @GetMapping("/course/{courseId}/users")
    public List<String> allUsersOfCourse(@PathVariable long courseId){
        return courseService.findById(courseId).getStudentsId().stream().map(e -> userService.findById(e).getFirstName()
                        +" "+ userService.findById(e).getLastName())
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    public UserResponse addUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        User user = new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName());
        return convertToUserResponse(userService.save(user));
    }

    @PostMapping("/courses")
    public CourseResponse addCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest) {
        Course course = new Course(courseCreateRequest.getName(), courseCreateRequest.getDescription());
        return convertToCourseResponse(courseService.save(course));
    }

    @GetMapping("/user/{userId}")
    public UserResponse getUser(@PathVariable long userId) {
        return convertToUserResponse(userService.findById(userId));
    }

    @GetMapping("/course/{courseId}")
    public CourseResponse getCourse(@PathVariable long courseId) {
        return convertToCourseResponse(courseService.findById(courseId));
    }

    @PutMapping("/user/{userId}/course/{courseId}")
    public UserResponse addCourseToUser(@PathVariable long userId, @PathVariable long courseId) {
        return convertToUserResponse(userService.addCourseToUser(userId, courseId));
    }
    @PutMapping("/user/{userId}/update")
    public UserResponse updateUser(@Valid @RequestBody UserCreateRequest userCreateRequest, @PathVariable long userId) {
        User user = new User(userCreateRequest.getFirstName(), userCreateRequest.getLastName());
        return convertToUserResponse(userService.update(user, userId));
    }
    @PutMapping("/user/{userId}/addGrade/{courseId}/{grade}")
    public String addGrade(@PathVariable long userId, @PathVariable long courseId, @PathVariable int grade){
        userService.addGrade(userId,courseId,grade);
        return "Grade : "+grade+" courseId: "+courseId+" to userId: "+userId;
    }

    @PutMapping("/course/{courseId}/user/{userId}")
    public CourseResponse addUserToCourse(@PathVariable long courseId, @PathVariable long userId) {
        return convertToCourseResponse(courseService.addUserToCourse(courseId, userId));
    }
    @PutMapping("/course/{courseId}/update")
    public CourseResponse updateCourse(@Valid @RequestBody CourseCreateRequest courseCreateRequest, @PathVariable long courseId) {
        Course course = new Course(courseCreateRequest.getName(), courseCreateRequest.getDescription());
        return convertToCourseResponse(courseService.update(course, courseId));
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
                user.getCoursesId().stream().map(e -> courseService.findById(e).getName())
                        .collect(Collectors.toList()));
        return userResponse;
    }

    private CourseResponse convertToCourseResponse(Course course) {
        CourseResponse courseResponse = new CourseResponse(course.getId(), course.getName(),
                course.getDescription(),
                course.getStudentsId().stream().map(e -> userService.findById(e).getFirstName()
                        +" "+ userService.findById(e).getLastName())
                .collect(Collectors.toList()));
        return courseResponse;
    }
}
