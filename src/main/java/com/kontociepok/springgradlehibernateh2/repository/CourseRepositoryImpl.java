package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.Course;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class CourseRepositoryImpl implements CourseRepository{

    public Map<Long, Course> database = new TreeMap<>();
    public static long count = 1;

    @Override
    public Course save(Course course) {
        course.setId(count);
        database.put(course.getId(), course);
        count++;
        return course;
    }

    @Override
    public Course findById(Long id) {
        return database.get(id);
    }

    @Override
    public List<Course> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public String deleteById(Long id) {
        database.remove(id);
        return "Delete Course Id: "+id;
    }

    @Override
    public void update(Course course) {
        database.put(course.getId(), course);
    }

    @Override
    public void clear() {
        database.clear();
        count = 1;
    }
}
