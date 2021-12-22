package com.kontociepok.springgradlehibernateh2.service;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        user.setCoursesId(courseId);
        Course course = courseRepository.findById(courseId);
        course.setStudentsId(userId);
        return user;
    }

    public void deleteUserAndDeleteUserFromCourses(long userId) {
        User user = userRepository.findById(userId);
        Set<Long> listCourses = user.getCoursesId();
        for(Long coursesId: listCourses){
            courseRepository.findById(coursesId).getStudentsId().remove(userId);
        }
        userRepository.deleteById(userId);
    }
}
