package com.kontociepok.springgradlehibernateh2.model;

import java.util.Objects;

public class UserDTO {
    private Long id;
    private String firstName;

    public UserDTO() {
    }

    public UserDTO(Long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO userDTO = (UserDTO) o;
        return Objects.equals(id, userDTO.id) && Objects.equals(firstName, userDTO.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName);
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                '}';
    }
}
