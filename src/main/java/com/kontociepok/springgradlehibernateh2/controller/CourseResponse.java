package com.kontociepok.springgradlehibernateh2.controller;

import java.util.List;
import java.util.Objects;

public class CourseResponse {

    private final Long id;
    private final String name;
    private List<String> users;

    public CourseResponse(Long id, String name, List<String> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseResponse that = (CourseResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CourseResponse{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", users=" + users +
                '}';
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getUsers() {
        return users;
    }
}
