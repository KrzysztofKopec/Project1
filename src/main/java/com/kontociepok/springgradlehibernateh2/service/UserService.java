package com.kontociepok.springgradlehibernateh2.service;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        user.addCourseId(courseId);
        Course course = courseRepository.findById(courseId);
        course.addStudentId(userId);
        return user;
    }

    public void deleteUserAndDeleteUserFromCourse(long userId) {
        User user = userRepository.findById(userId);
        Set<Long> listCourses = user.getCoursesId();
        for(Long coursesId: listCourses){
            courseRepository.findById(coursesId).getStudentsId().remove(userId);
        }
        userRepository.deleteById(userId);
    }

    public User findById(long userId) {
        return userRepository.findById(userId);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }
}
