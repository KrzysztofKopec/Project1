package com.kontociepok.springgradlehibernateh2.model;

import java.util.*;

public class User {

    public enum TypeUser {STUDENT, TEACHER;}

    private Long id;

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String pesel;

    private User.TypeUser typeUser;

    private Set<Long> coursesId;

    private Map<Long, List<Grade>> gradesCourses;

    public User(){

    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        coursesId = new TreeSet<>();
        gradesCourses = new HashMap<>();
    }

    public User(Long id, String login, String password, String firstName, String lastName, String pesel, TypeUser typeUser) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.typeUser = typeUser;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", pesel='" + pesel + '\'' +
                ", typeUser=" + typeUser +
                ", courses=" + coursesId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public TypeUser getTypeUser() {
        return typeUser;
    }

    public void setTypeUser(TypeUser typeUser) {
        this.typeUser = typeUser;
    }

    public Set<Long> getCoursesId() {
        return coursesId;
    }

    public void addCourseId(Long courseId) {
        this.coursesId.add(courseId);
    }

    public Map<Long, List<Grade>> getGradesCourses() {
        return gradesCourses;
    }

    public void addingACourseGrade(Long courseId, Grade grade) {

        gradesCourses.computeIfAbsent(courseId, k -> new ArrayList<>()).add(grade);

//        List<Grade> lista;
//        if(gradesCourses.containsKey(courseId)) {
//            lista= gradesCourses.get(courseId);
//            lista.add(grade);
//            gradesCourses.put(courseId, lista);
//        }else{
//            lista = new ArrayList<>();
//            lista.add(grade);
//            gradesCourses.put(courseId, lista);
//        }
    }
}
