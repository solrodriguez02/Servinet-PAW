package ar.edu.itba.paw.services;

import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> findById(long id);
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    void makeProvider(long userid);
    User create(String username, String name,String surname, String password, String email, String telephone);
    void changeUsername(long userid,String value);
    void changeUserType(long userid);
    boolean isProvider(long userid);
    void changeEmail(long userid,String value);
    void changePassword(String email,String value);
}