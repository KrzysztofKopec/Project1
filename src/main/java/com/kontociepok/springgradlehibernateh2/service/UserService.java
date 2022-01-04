package com.kontociepok.springgradlehibernateh2.service;

import com.kontociepok.springgradlehibernateh2.model.Course;
import com.kontociepok.springgradlehibernateh2.model.User;
import com.kontociepok.springgradlehibernateh2.repository.CourseRepository;
import com.kontociepok.springgradlehibernateh2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void addGrade(long userId, long courseId, int grade) {
        User user = userRepository.findById(userId);
        user.addingACourseGrade(courseId,grade);
        userRepository.update(user);
    }

    public String courseGrades(long userId, long courseId) {
        User user = userRepository.findById(userId);
        String nameCourse = courseRepository.findById(courseId).getName();
        ArrayList<Integer> grades = user.getGradesCourses().get(courseId);
        String gradesToString = grades.toString();
        return nameCourse+" "+gradesToString;
    }
}
