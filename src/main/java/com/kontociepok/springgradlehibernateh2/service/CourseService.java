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
public class CourseService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;

    @Autowired
    public CourseService(UserRepository userRepository, CourseRepository courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }
    public Course addUserToCourse(Long courseId, Long userId) {
        Course course = courseRepository.findById(courseId);
        course.setStudents(userRepository.findById(userId));
        User user = userRepository.findById(userId);
        user.setCourses(courseRepository.findById(courseId));
        return course;
    }

    public void deleteCourseAndDeleteCourseFromUsers(long courseId) {
        Course course = courseRepository.findById(courseId);
        List<Long> listUsers = course.getStudents().stream().map(User::getId).collect(Collectors.toList());
        for(Long userId: listUsers){
            userRepository.findById(userId).getCourses().remove(course);
        }
        courseRepository.deleteById(courseId);
    }
}
