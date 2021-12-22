package com.kontociepok.springgradlehibernateh2.service;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Autowired
    public UserService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    public User addCourseToUser(Long userId, Long courseId){
        User user = userRepository.findById(userId);
        user.setCourses(courseRepository.findById(courseId));
        Course course = courseRepository.findById(courseId);
        course.setStudents(userRepository.findById(userId));
        return user;
    }

    public void deleteUserAndDeleteUserFromCourses(long userId) {
        User user = userRepository.findById(userId);
        List<Long> listCourses = user.getCourses().stream().map(Course::getId).collect(Collectors.toList());
        for(Long coursesId: listCourses){
            courseRepository.findById(coursesId).getStudents().remove(user);
        }
        userRepository.deleteById(userId);
    }
}
