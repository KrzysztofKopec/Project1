package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.User;
import java.util.List;

public interface UserRepository {

    public User save(User user);
    public User findById(Long id);
    public List<User> findAll();
    public String deleteById(Long id);
    public void update(User user);
    public void clear();
}
