package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository {

    public User save(User user);
    public User findById(Long id);
    public List<User> findAll();
    public void deleteById(Long id);
    public void clear();
}
