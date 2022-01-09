package com.kontociepok.springgradlehibernateh2.controller;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Objects;

public class GradeCreateRequest {

    private final Long courseId;

    @Min(1)
    @Max(6)
    private final Integer grade;

    public GradeCreateRequest(Long courseId, @Min(1) @Max(6)Integer grade) {
        this.courseId = courseId;
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "GradeCreateRequest{" +
                "courseId=" + courseId +
                ", grade=" + grade +
                '}';
    }

    public Long getCourseId() {
        return courseId;
    }

    public Integer getGrade() {
        return grade;
    }
}
