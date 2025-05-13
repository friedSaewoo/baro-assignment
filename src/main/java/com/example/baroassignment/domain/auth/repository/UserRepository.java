package com.example.baroassignment.domain.auth.repository;

import com.example.baroassignment.domain.User;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class UserRepository {
    private final Map<Long, User> repository = new HashMap<>();

    public User findById(Long id) {
        return repository.get(id);
    }

    public void save(User user) {
        repository.put(user.getId(), user);
    }

    public Optional<User> findByUsername(String username) {
        return repository.values().stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst();
    }

    public Collection<User> findAll() {
        return repository.values();
    }
}
