package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.Course;
import java.util.List;

public interface CourseRepository {

    public Course save(Course course);
    public Course findById(Long id);
    public List<Course> findAll();
    public String deleteById(Long id);
    public void update(Course course);
    public void clear();
}
