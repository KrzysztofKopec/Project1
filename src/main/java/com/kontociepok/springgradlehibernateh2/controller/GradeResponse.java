package com.kontociepok.springgradlehibernateh2.controller;

import com.kontociepok.springgradlehibernateh2.model.Grade;

import java.util.List;

public class GradeResponse {
    private final String courseName;
    private final List<Grade> gradesList;

    public GradeResponse(String courseName, List<Grade> gradesList) {
        this.courseName = courseName;
        this.gradesList = gradesList;
    }

    @Override
    public String toString() {
        return "GradeResponse{" +
                "courseName=" + courseName +
                ", gradesList=" + gradesList +
                '}';
    }

    public String getCourseName() {
        return courseName;
    }

    public List<Grade> getGradesList() {
        return gradesList;
    }
}
