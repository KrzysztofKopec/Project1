package com.kontociepok.springgradlehibernateh2.repository;

import com.kontociepok.springgradlehibernateh2.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Repository
public class UserRepositoryImpl implements UserRepository{

    public Map<Long,User> database = new TreeMap<>();
    public static long count = 1;


    @Override
    public void save(User user) {
        user.setId(count);
        database.put(count, user);
        count++;
    }

    @Override
    public User findById(Long id) {
        return database.get(id);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(database.values());
    }

    @Override
    public void deleteById(Long id) {
        database.remove(id);
    }

}
