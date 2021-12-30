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
        course.addStudentId(userId);
        User user = userRepository.findById(userId);
        user.addCourseId(courseId);
        return course;
    }

    public void deleteCourseAndDeleteCourseFromUsers(long courseId) {
        Course course = courseRepository.findById(courseId);
        Set<Long> listUsers = course.getStudentsId();
        for(Long userId: listUsers){
            userRepository.findById(userId).getCoursesId().remove(courseId);
        }
        courseRepository.deleteById(courseId);
    }

    public Course findById(long courseId) {
        return courseRepository.findById(courseId);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }
}
