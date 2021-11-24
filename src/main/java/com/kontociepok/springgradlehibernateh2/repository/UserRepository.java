package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.User;

import java.util.List;

public interface UserRepository {

    public void save(User user);
    public User findById(Long id);
    public List<User> findAll();
    public void deleteById(Long id);
}
