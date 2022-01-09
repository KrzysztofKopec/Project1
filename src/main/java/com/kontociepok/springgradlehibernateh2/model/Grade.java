package com.kontociepok.springgradlehibernateh2.model;

import java.util.List;
import java.util.Objects;

public class Grade {

    private Integer grade;

    public Grade() {
    }

    public Grade(Integer grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grade grade1 = (Grade) o;
        return Objects.equals(grade, grade1.grade);
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade);
    }

    @Override
    public String toString() {
        return "Grade{" +
                "grade=" + grade +
                '}';
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
