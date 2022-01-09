package com.kontociepok.springgradlehibernateh2.service;

import com.kontociepok.springgradlehibernateh2.controller.GradeResponse;
import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.Grade;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
        userRepository.update(user);
        Course course = courseRepository.findById(courseId);
        course.addStudentId(userId);
        courseRepository.update(course);
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

    public User update(User user, long userId) {
        User user1 = userRepository.findById(userId);
        user1.setId(userId);
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        userRepository.update(user1);
        return user1;
    }

    public void addGrade(long userId, long courseId, Grade grade) throws Exception {
        User user = userRepository.findById(userId);
        Optional<User> optionalUser = Optional.ofNullable(user);
        if(optionalUser.map(e -> e.getCoursesId().contains(courseId)).isPresent())
        {
                user.addingACourseGrade(courseId, grade);
                userRepository.update(user);
            } else throw new Exception("Course not exists !!!!!");
    }

    public String courseGrades(long userId, long courseId) {
        User user = userRepository.findById(userId);
        if(user != null) {
            if (user.getCoursesId().contains(courseId)) {
                String nameCourse = courseRepository.findById(courseId).getName();
                List<Grade> grades = user.getGradesCourses().get(courseId);
                String gradesToString = grades.toString();
                return nameCourse + " " + gradesToString;
            } else {
                return "there is no course on id :" + courseId;
            }
        }else {
            return "there is no user on id: " + userId;
        }
    }
}
