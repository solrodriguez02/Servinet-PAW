package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;

import java.util.Optional;

public interface SecurityService {
    Optional<String> getCurrentUserEmail();
    Optional<User> getCurrentUser();
}
