package com.kontociepok.springgradlehibernateh2.controller;

import javax.validation.constraints.NotEmpty;
import java.util.Objects;

public class CourseCreateRequest {

    @NotEmpty(message = "please enter name")
    private final String name;
    @NotEmpty(message = "please enter description")
    private final String description;

    public CourseCreateRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseCreateRequest that = (CourseCreateRequest) o;
        return Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "CourseCreateRequest{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
