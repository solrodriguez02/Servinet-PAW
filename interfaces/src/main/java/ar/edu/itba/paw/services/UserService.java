package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findById(long id);

    User create(String username);
}