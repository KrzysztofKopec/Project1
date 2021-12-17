package com.kontociepok.springgradlehibernateh2.controller;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class UserCreateRequestTest {

    @Test
    public void whenNotEmptyName_thenNoConstraintViolations() {
        //given
        UserCreateRequest user = new UserCreateRequest("dke","Bean");

        //when
        var result = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserCreateRequest>> violations = result.validate(user);

        //then
        assertThat(violations.size()).isEqualTo(0);
    }

    @Test
    public void whenEmptyFirstName_thenOneConstraintViolation() {
        //given
        UserCreateRequest user = new UserCreateRequest("","Bean");

        //when
        var result = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserCreateRequest>> violations = result.validate(user);

        //then
        assertThat(violations.size()).isEqualTo(1);
    }
    @Test
    public void whenEmptyTwoFields_thenTwoConstraintViolation() {
        //given
        UserCreateRequest user = new UserCreateRequest("","");

        //when
        var result = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<UserCreateRequest>> violations = result.validate(user);

        //then
        assertThat(violations.size()).isEqualTo(2);
    }
}